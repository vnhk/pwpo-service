package com.pwpo.common.service;

import com.bervan.ieentities.BaseExcelExport;
import com.bervan.ieentities.BaseExcelImport;
import com.bervan.ieentities.ExcelIEEntity;
import com.bervan.ieentities.LoadIEAvailableEntities;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.SearchRequest;
import com.pwpo.common.search.SearchService;
import com.pwpo.common.search.model.SortDirection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class IEDataService {
    @Value("${import.export.service.file.storage.url}")
    private String FOLDER;
    private final SearchService searchService;

    public UrlResource export() throws IOException {
        BaseExcelExport baseExcelExport = new BaseExcelExport();
        List<? extends ExcelIEEntity<?>> entities = getAll();
        Workbook workbook = baseExcelExport.exportExcel(entities, null);
        String fileName = LocalDate.now().toString();
        File stored = baseExcelExport.save(workbook, FOLDER, fileName);

        return new UrlResource(stored.toPath().toUri());
    }

    private List<? extends ExcelIEEntity<?>> getAll() {
        List<Class<?>> allClassesAvailable = new LoadIEAvailableEntities().getSubclassesOfExcelEntity("com.pwpo");
        List result = new ArrayList<>();
        for (Class<?> aClass : allClassesAvailable) {
            result.addAll(getExcelIEEntities((Class<? extends ExcelIEEntity<?>>) aClass));
        }

        return result;
    }

    private List<? extends ExcelIEEntity<?>> getExcelIEEntities(Class<? extends ExcelIEEntity<?>> classToSearch) {
        SearchRequest request = new SearchRequest();
        request.resultType = classToSearch.getName();
        return (List<? extends ExcelIEEntity<?>>) searchService.search(request, getSearchOptionsForExport(request))
                .getResultList();
    }

    private SearchQueryOption getSearchOptionsForExport(SearchRequest projectRequest) {
        return new SearchQueryOption(SortDirection.ASC, "id", 1, 10000000, projectRequest.resultType);
    }

    public void importData(MultipartFile file) throws IOException {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xlsx")) {
            throw new RuntimeException("Could not import data! Only xlsx files are supported!");
        }

        File excelWithData = null;
        try {
            excelWithData = storeTmp(file);
            List<Class<?>> allClassesAvailableToImport = new LoadIEAvailableEntities().getSubclassesOfExcelEntity("com.pwpo");
            BaseExcelImport baseExcelImport = new BaseExcelImport(allClassesAvailableToImport);
            Workbook workbook = baseExcelImport.load(excelWithData);
            List<?> entitiesToSave = baseExcelImport.importExcel(workbook);
        } catch (Exception e) {
            log.error("Could not perform importing data from excel!");
            throw new RuntimeException(e);
        } finally {
            if (excelWithData != null) {
                Files.deleteIfExists(excelWithData.toPath());
            }
        }
    }

    private File storeTmp(MultipartFile file) {
        String fileName = LocalDate.now() + "_" + file.getOriginalFilename();
        File fileTmp = new File(fileName);
        try {
            File directory = new File(FOLDER + File.separator);
            directory.mkdirs();
            Files.copy(file.getInputStream(), fileTmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return fileTmp;
    }
}
