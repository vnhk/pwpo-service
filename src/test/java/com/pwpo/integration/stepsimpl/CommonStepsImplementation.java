package com.pwpo.integration.stepsimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.TestUtils;
import com.pwpo.common.enums.DataEnum;
import com.pwpo.common.exception.ExceptionBadRequestResponse;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.search.model.SortDirection;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonStepsImplementation {
    public APIResponse apiResponse;
    public MvcResult mvcResult;
    public ObjectMapper mapper = TestUtils.getObjectMapper();
    protected MockMvc mockMvc;

    public void clientReceivesAPIResponse(DataTable dataTable) throws Exception {
        if (apiResponse == null) {
            apiResponse = TestUtils.convertAPIResponse(mvcResult.getResponse(), Map.class, mapper);
        }

        Map<String, String> data = dataTable.asMaps().get(0);

        assertThat(Integer.parseInt(data.get("allFound")))
                .isEqualTo(apiResponse.getAllFound());

        assertThat(Integer.parseInt(data.get("currentPage")))
                .isEqualTo(apiResponse.getCurrentPage());

        assertThat(Integer.parseInt(data.get("currentFound")))
                .isEqualTo(apiResponse.getCurrentFound());
    }

    public void clientReceivesStatus(int status) {
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(status);
    }

    public void clientReceivesBadRequestDetails(DataTable dataTable) throws Exception {
        ExceptionBadRequestResponse res = TestUtils.convertResponse(mvcResult.getResponse(), ExceptionBadRequestResponse.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);

        assertThat(data.get("field")).isEqualTo(res.getField());
        assertThat(data.get("code")).isEqualTo(res.getCode().name());
        assertThat(data.get("message")).isEqualTo(res.getMessage());
    }

    protected String getDefaultGetParams(Class<? extends Persistable> entityToFind) {
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
                } else if (type.equals(Integer.class)) {
                    value = Integer.valueOf((String) value);
                } else if (type.isEnum()) {
                    DataEnum[] enumConstants = (DataEnum[]) type.getEnumConstants();
                    String displayName = (String) value;
                    value = Arrays.stream(enumConstants)
                            .filter(e -> e.getDisplayName().equals(displayName))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Enum value for DTO should be set with display name!"));
                } else if (type.equals(LocalDate.class)) {
                    value = LocalDate.parse((String) value);
                }

                dtoField.setAccessible(true);
                dtoField.set(dto, value);
                dtoField.setAccessible(false);
            }
        }
        return dto;
    }

}
