package com.pwpo.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.user.model.APIResponse;
import com.pwpo.user.model.ItemDTO;
import com.pwpo.user.model.Itemable;
import com.pwpo.common.search.model.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemMapper {
    private final ObjectMapper mapper;

    public ItemDTO mapToDTO(Itemable item, Class<? extends ItemDTO> dtoClass) {
        try {
            String itemAsString = mapper.writeValueAsString(item);
            return mapper.readValue(itemAsString, dtoClass);
        } catch (JsonProcessingException e) {
            log.error("Could not map item!", e);
            throw new RuntimeException("Could not map item!");
        }
    }

    public APIResponse mapToAPIResponse(SearchResponse response, Class<? extends ItemDTO> dtoClass) {
        List<ItemDTO> items = new ArrayList<>();

        List<? extends Itemable> resultList = response.getResultList();
        resultList.forEach(e -> items.add(mapToDTO(e, dtoClass)));

        return new APIResponse(items, response.getCurrentFound(), response.getCurrentPage(), response.getAllFound());
    }

    public APIResponse mapToAPIResponse(Itemable item, Class<? extends ItemDTO> dtoClass) {
        List<ItemDTO> items = new ArrayList<>();
        items.add(mapToDTO(item, dtoClass));

        return new APIResponse(items, 1, 1, 1);
    }

    public <T> T mapToObj(ItemDTO dto, Class<T> objClass) {
        try {
            String itemAsString = mapper.writeValueAsString(dto);
            return mapper.readValue(itemAsString, objClass);
        } catch (JsonProcessingException e) {
            log.error("Could not map item!", e);
            throw new RuntimeException("Could not map item!");
        }
    }
}
