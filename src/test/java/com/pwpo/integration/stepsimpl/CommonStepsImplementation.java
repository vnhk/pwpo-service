package com.pwpo.integration.stepsimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.TestUtils;
import com.pwpo.common.enums.DataEnum;
import com.pwpo.common.exception.ApiError;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Persistable;
import com.pwpo.common.model.diff.CompareResponseDTO;
import com.pwpo.common.model.diff.DiffAttribute;
import com.pwpo.common.model.diff.DiffWord;
import com.pwpo.common.model.dto.HistoryReponseDTO;
import com.pwpo.common.search.model.SortDirection;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.*;

import static com.pwpo.integration.IntegrationDataHolder.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonStepsImplementation {
    public ObjectMapper mapper = TestUtils.getObjectMapper();
    public MockMvc mockMvc;

    public CommonStepsImplementation(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        clean();
    }

    public void clientReceivesAPIResponse(DataTable dataTable) throws Exception {
        if (apiResponse() == null) {
            apiResponse(TestUtils.convertAPIResponse(mvcResult().getResponse(), Map.class, mapper));
        }

        Map<String, String> data = dataTable.asMaps().get(0);

        assertThat(Integer.parseInt(data.get("allFound")))
                .isEqualTo(apiResponse().getAllFound());

        assertThat(Integer.parseInt(data.get("currentPage")))
                .isEqualTo(apiResponse().getCurrentPage());

        assertThat(Integer.parseInt(data.get("currentFound")))
                .isEqualTo(apiResponse().getCurrentFound());
    }

    public void clientReceivesStatus(int status) {
        assertThat(mvcResult().getResponse().getStatus()).isEqualTo(status);
    }

    public void clientReceivesBadRequestDetails(DataTable dataTable) throws Exception {
        List<ApiError> res =
                TestUtils.convertResponse(mvcResult().getResponse(), List.class, mapper, ApiError.class);

        assertThat(dataTable.asMaps().size()).isEqualTo(res.size());

        for (int i = 0; i < dataTable.asMaps().size(); i++) {
            Map<String, String> data = dataTable.asMaps().get(i);
            String field = data.get("field");
            String code = data.get("code");
            String message = data.get("message");

            Optional<ApiError> responseForFieldOpt
                    = res.stream()
                    .filter(e -> Objects.equals(e.getField(), field))
                    .filter(e -> Objects.equals(e.getCode().name(), code))
                    .filter(e -> Objects.equals(e.getMessage(), message))
                    .findAny();

            assertThat(responseForFieldOpt)
                    .withFailMessage(String.format("Could not find match for field: %s, code: %s and message: %s", field, code, message))
                    .isPresent();
        }
    }

    protected String getDefaultGetParams(Class<? extends Persistable> entityToFind) {
        String entity = entityToFind == null ? null : entityToFind.getName();
        return TestUtils.buildParams(1, 1000, "id", SortDirection.ASC, entity, "");
    }

    public void performReceiveHistory(DataTable dataTable) throws Exception {
        APIResponse<HistoryReponseDTO> historyResponse
                = TestUtils.convertAPIResponse(mvcResult().getResponse(), HistoryReponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        HistoryReponseDTO history = historyResponse.getItems().get(0);

        assertThat(Long.parseLong(data.get("id"))).isEqualTo(history.getId());
        assertThat(data.get("expired")).isEqualTo(history.getExpired().toString());
        assertThat(data.get("editor")).isEqualTo(history.getEditor().getNick());
    }

    public <T> T buildDTO(DataTable dataTable, Class<T> dtoClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return buildDTO(dataTable, dtoClass, 0);
    }

    private <T> T buildDTO(DataTable dataTable, Class<T> dtoClass, int i) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T dto = dtoClass.getDeclaredConstructor().newInstance();
        Map<String, String> data = dataTable.asMaps().get(i);
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
                } else if (type.equals(List.class)) {
                    String[] values = ((String) value).split(",");
                    value = Arrays.stream(values).toList();
                }

                dtoField.setAccessible(true);
                dtoField.set(dto, value);
                dtoField.setAccessible(false);
            }
        }
        return dto;
    }

    public <T> List<T> buildListDTO(DataTable dataTable, Class<T> dtoClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> res = new ArrayList<>();
        for (int i = 0; i < dataTable.asMaps().size(); i++) {
            res.add(buildDTO(dataTable, dtoClass, i));
        }
        return res;
    }

    public void performCheckComparisonDetails(DataTable dataTable) throws Exception {
        List<Map<String, String>> maps = dataTable.asMaps();
        assertThat(maps).withFailMessage("2 rows table is required for this step!").hasSize(1);
        Map<String, String> map = maps.get(0);
        APIResponse<CompareResponseDTO> compareResponseDTOAPIResponse =
                TestUtils.convertAPIResponse(mvcResult().getResponse(), CompareResponseDTO.class, mapper);

        CompareResponseDTO responseDTO = compareResponseDTOAPIResponse.getItems().get(0);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            Optional<DiffAttribute> diffOpt = responseDTO.getDiff()
                    .stream().filter(e -> ((DiffAttribute) e).getAttribute().equals(field))
                    .findFirst();

            assertThat(diffOpt)
                    .withFailMessage("Field " + field + " not found in response!")
                    .isPresent();

            DiffAttribute diffAttribute = diffOpt.get();

            List<DiffWord> diff = diffAttribute.getDiff();
            StringBuilder res = new StringBuilder();
            for (DiffWord diffWord : diff) {
                res.append(diffWord.toString()).append(" ");
            }
            assertThat(value).isEqualTo(res.substring(0, res.length() - 1));
        }
    }

    public void loginWithRoles(String username, String roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles.split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        User principal = new User(username, "testPassword", authorities);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
