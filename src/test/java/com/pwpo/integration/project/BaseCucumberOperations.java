package com.pwpo.integration.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.TestUtils;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.search.model.SortDirection;
import io.cucumber.datatable.DataTable;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseCucumberOperations {
    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper mapper = TestUtils.getObjectMapper();

    public String getDefaultGetParams(Class<? extends Persistable> entityToFind) {
        return TestUtils.buildParams(1, 1000, "id", SortDirection.ASC, entityToFind.getName(), "");
    }

    public <T> T buildDTO(DataTable dataTable, Class<T> dtoClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T dto = dtoClass.getDeclaredConstructor().newInstance();
        Map<String, String> data = dataTable.asMaps().get(0);
        for (Field dtoField : dtoClass.getDeclaredFields()) {
            String dtoFieldName = dtoField.getName();
            Object value = data.get(dtoFieldName);
            if (StringUtils.isNotBlank((String) value)) {
                Class<?> type = dtoField.getType();

                if (type.equals(Long.class)) {
                    value = Long.valueOf((String) value);
                } else if (type.equals(Status.class)) {
                    String statusDisplayName = (String) value;
                    value = Arrays.stream(Status.values())
                            .filter(e -> e.getDisplayName().equals(statusDisplayName))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Enum value for DTO should be set with display name!"));
                }

                dtoField.setAccessible(true);
                dtoField.set(dto, value);
                dtoField.setAccessible(false);
            }
        }
        return dto;
    }

}
