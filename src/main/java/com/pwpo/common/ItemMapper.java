package com.pwpo.common;

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

    public ItemDTO mapToDTO(Itemable task, Class<? extends ItemDTO> dtoClass) {
        try {
            String projectAsString = mapper.writeValueAsString(task);
            return mapper.readValue(projectAsString, dtoClass);
        } catch (JsonProcessingException e) {
            log.error("Could not map item!", e);
            throw new RuntimeException("Could not map item!");
        }
    }
}
