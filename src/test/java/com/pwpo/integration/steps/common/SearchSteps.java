package com.pwpo.integration.steps.common;

import com.pwpo.integration.IntegrationDataHolder;
import com.pwpo.integration.stepsimpl.SearchStepsImplementation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

public class SearchSteps extends BaseCucumberStep {
    public SearchStepsImplementation searchStepsImplementation;

    @Autowired
    private CommonSteps commonSteps;

    @Before
    public void setUp() {
        searchStepsImplementation = new SearchStepsImplementation(mockMvc);
        commonSteps.commonStepsImplementation = searchStepsImplementation;
        IntegrationDataHolder.clean();
    }

    @When("the client wants to search for {string} with criteria")
    public void theClientWantsToSearchForWithCriteria(String resultType, DataTable dataTable) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        searchStepsImplementation.initWithCriteria(resultType, dataTable);
    }

    @And("criteria are combined into groups")
    public void criteriaAreCombinedIntoGroups(DataTable dataTable) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        searchStepsImplementation.addGroups(dataTable);
    }

    @Then("the client performs search")
    public void theClientPerformsSearch() throws Exception {
        searchStepsImplementation.performSearch();
    }
}
