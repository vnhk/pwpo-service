package com.pwpo.integration.steps.common;

import com.pwpo.integration.IntegrationDataHolder;
import com.pwpo.integration.stepsimpl.CommonStepsImplementation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CommonSteps extends BaseCucumberStep {
    public CommonStepsImplementation commonStepsImplementation = new CommonStepsImplementation(mockMvc);

    @Given("the {string} user with roles: {string} is logged")
    public void loginWithRoles(String username, String roles) {
        IntegrationDataHolder.clean();
        commonStepsImplementation.loginWithRoles(username, roles);
    }

    @Then("the client receives APIResponse")
    public void clientReceivesAPIResponse(DataTable dataTable) throws Exception {
        commonStepsImplementation.clientReceivesAPIResponse(dataTable);
    }

    @Then("the client receives {int} status")
    public void theClientReceivesStatus(int status) {
        commonStepsImplementation.clientReceivesStatus(status);
    }

    @Then("the client receives {string} status")
    public void theClientReceivesStatus(String status) {
        int intStatus = switch (status) {
            case "success" -> 200;
            case "error" -> 400;
            default -> 0;
        };

        commonStepsImplementation.clientReceivesStatus(intStatus);
    }

    @And("the client receives bad request details")
    public void theClientReceivesBadRequestDetails(DataTable dataTable) throws Exception {
        commonStepsImplementation.clientReceivesBadRequestDetails(dataTable);
    }

    @And("the client sees history")
    public void theClientSeesHistory(DataTable dataTable) throws Exception {
        commonStepsImplementation.performReceiveHistory(dataTable);
    }

    @And("the client sees comparison details")
    public void theClientSeesComparisonDetails(DataTable dataTable) throws Exception {
        commonStepsImplementation.performCheckComparisonDetails(dataTable);
    }
}
