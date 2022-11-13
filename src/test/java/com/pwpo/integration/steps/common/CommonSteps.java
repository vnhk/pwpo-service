package com.pwpo.integration.steps.common;

import com.pwpo.integration.stepsimpl.CommonStepsImplementation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CommonSteps extends BaseCucumberStep {
    public CommonStepsImplementation commonStepsImplementation = new CommonStepsImplementation(mockMvc);

    @Then("the client receives APIResponse")
    public void clientReceivesAPIResponse(DataTable dataTable) throws Exception {
        commonStepsImplementation.clientReceivesAPIResponse(dataTable);
    }

    @Then("the client receives {int} status")
    public void theClientReceivesStatus(int status) {
        commonStepsImplementation.clientReceivesStatus(status);
    }

    @And("the client receives bad request details")
    public void theClientReceivesBadRequestDetails(DataTable dataTable) throws Exception {
        commonStepsImplementation.clientReceivesBadRequestDetails(dataTable);
    }

    @And("the client sees history")
    public void theClientSeesHistory(DataTable dataTable) throws Exception {
        commonStepsImplementation.performReceiveHistory(dataTable);
    }
}
