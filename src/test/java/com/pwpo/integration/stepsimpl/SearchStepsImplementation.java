package com.pwpo.integration.stepsimpl;

import com.pwpo.TestUtils;
import com.pwpo.common.search.Criterion;
import com.pwpo.common.search.Group;
import com.pwpo.common.search.SearchRequest;
import io.cucumber.datatable.DataTable;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.InvocationTargetException;

import static com.pwpo.integration.IntegrationDataHolder.mvcResult;

public class SearchStepsImplementation extends CommonStepsImplementation {
    SearchRequest searchRequest;

    public SearchStepsImplementation(MockMvc mockMvc) {
        super(mockMvc);
    }

    public void initWithCriteria(String resultType, DataTable dataTable) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        searchRequest = new SearchRequest();
        searchRequest.resultType = resultType;
        searchRequest.criteria = buildListDTO(dataTable, Criterion.class);
    }

    public void performSearch() throws Exception {
        mvcResult(mockMvc.perform(TestUtils.buildPostRequest("/search", searchRequest, mapper)).andReturn());
    }

    public void addGroups(DataTable dataTable) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        searchRequest.groups = buildListDTO(dataTable, Group.class);
    }
}
