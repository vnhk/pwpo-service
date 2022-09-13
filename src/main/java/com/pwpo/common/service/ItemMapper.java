package com.pwpo.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.common.model.Itemable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
