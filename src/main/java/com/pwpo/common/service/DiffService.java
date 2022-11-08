package com.pwpo.common.service;

import com.pwpo.common.model.diff.DiffType;
import com.pwpo.common.model.diff.DiffWord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiffService {

    private final static String SPACE_REGEX = " ";

    public List<DiffWord> diff(String fst, String snd) {
        // TODO: 08.11.2022 fst=Project that is used for testing application | snd=Edited Desc | exception!!!
        // TODO: 08.11.2022 snd all different values | exception!!!
        String[] wordsFst = fst.split(SPACE_REGEX);
        String[] wordsSnd = snd.split(SPACE_REGEX);

        int fstLen = wordsFst.length;
        int sndLen = wordsSnd.length;

        if (fstLen == 1 || sndLen == 1) {
            if (fst.equals(snd)) {
                return Collections.singletonList(new DiffWord(fst, DiffType.EQUAL));
            }
            List<DiffWord> diffWords = new ArrayList<>();
            diffWords.add(new DiffWord(fst, DiffType.REMOVED));
            diffWords.add(new DiffWord(snd, DiffType.ADDED));

            return diffWords;
        }

        int[][] tab = prepareTable(wordsFst, wordsSnd);
        List<String> commonWords = getCommonWords(wordsFst, wordsSnd, tab);

//        System.out.println(Arrays.deepToString(tab));
//        System.out.println(commonWords);

        return compare(wordsFst, wordsSnd, fstLen, sndLen, commonWords);
    }

    private List<DiffWord> compare(String[] wordsFst, String[] wordsSnd, int fstLen, int sndLen, List<String> commonWords) {
        List<DiffWord> result = new ArrayList<>();

        int ai = 0;
        int ri = 0;
        int bi = 0;

        boolean riExceeded = false;
        boolean aiExceeded = false;
        boolean biExceeded = false;

        while (!riExceeded || !aiExceeded || !biExceeded) {
            if (!riExceeded) {
                if (wordsFst[ai].equals(commonWords.get(ri))) {
                    if (wordsFst[ai].equals(wordsSnd[bi])) {
                        whenWordsAreEqual(result, wordsFst[ai]);
                        bi++;
                        ai++;
                        ri++;
                    } else {
                        whenAddedToSndString(result, wordsSnd[bi]);
                        bi++;
                    }
                } else {
                    whenRemovedFromFstString(result, wordsFst[ai]);
                    ai++;
                }

                if (!riExceeded && ri >= commonWords.size()) {
                    riExceeded = true;
                }
            } else {
                if (aiExceeded) {
                    whenAddedToSndString(result, wordsSnd[bi]);
                    bi++;
                } else {
                    whenRemovedFromFstString(result, wordsFst[ai]);
                    ai++;
                }
            }

            if (!aiExceeded && ai >= fstLen) {
                aiExceeded = true;
            }

            if (!biExceeded && bi >= sndLen) {
                biExceeded = true;
            }
        }

        return result;
    }

    private List<String> getCommonWords(String[] wordsFst, String[] wordsSnd, int[][] tab) {
        int a = wordsFst.length;
        int b = wordsSnd.length;

        List<String> commonWords = new LinkedList<>();

        while (tab[a][b] != 0) {
            if (!wordsFst[a - 1].equals(wordsSnd[b - 1])) {
                if (tab[a][b - 1] >= tab[a - 1][b]) {
                    b--;
                } else {
                    a--;
                }
            } else {
                commonWords.add(0, wordsFst[a - 1]);
                b--;
                a--;
            }
        }

        return commonWords;
    }

    private void whenRemovedFromFstString(List<DiffWord> result, String word) {
        result.add(new DiffWord(word, DiffType.REMOVED));
    }

    private void whenAddedToSndString(List<DiffWord> result, String word) {
        result.add(new DiffWord(word, DiffType.ADDED));
    }

    private void whenWordsAreEqual(List<DiffWord> result, String word) {
        result.add(new DiffWord(word, DiffType.EQUAL));
    }

    private int[][] prepareTable(String[] wordsFst, String[] wordsSnd) {
        int fstLen = wordsFst.length;
        int sndLen = wordsSnd.length;

        int[][] tab = initTabWithZeroes(fstLen, sndLen);

        for (int a = 1; a <= fstLen; a++) {
            for (int b = 1; b <= sndLen; b++) {
                if (wordsFst[a - 1].equals(wordsSnd[b - 1])) {
                    tab[a][b] = tab[a - 1][b - 1] + 1;
                } else {
                    tab[a][b] = max(tab[a][b - 1], tab[a - 1][b]);
                }
            }
        }

        return tab;
    }

    private int[][] initTabWithZeroes(int fstLen, int sndLen) {
        int[][] tab = new int[fstLen + 1][sndLen + 1];

        for (int[] t : tab) {
            Arrays.fill(t, 0);
        }

        return tab;
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }
}
