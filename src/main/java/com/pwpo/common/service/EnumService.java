package com.pwpo.common.service;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.EnumDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnumService {
    public APIResponse getEnumByName(String name) {
        try {
            List<EnumDTO> result = new ArrayList<>();

            Class<?> enumClass = Class.forName(name);
            Object[] enumConstants = enumClass.getEnumConstants();
            for (Object enumConstant : enumConstants) {
                Method m = enumClass.getMethod("getDisplayName");
                String displayName = (String) m.invoke(enumConstant);
                String internalName = enumConstant.toString();
                result.add(new EnumDTO(internalName, displayName));
            }
            return new APIResponse(result, result.size(), 1, result.size());
        } catch (Exception e) {
            log.error("Could not get enum!", e);
            throw new RuntimeException("Could not get enum!");
        }
    }
}
