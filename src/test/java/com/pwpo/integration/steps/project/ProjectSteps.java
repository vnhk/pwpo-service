package com.pwpo.integration.steps.project;

import com.pwpo.integration.steps.common.BaseCucumberStep;
import com.pwpo.integration.steps.common.CommonSteps;
import com.pwpo.integration.stepsimpl.ProjectStepsImplementation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectSteps extends BaseCucumberStep {
    private Long id;
    private ProjectStepsImplementation projectCucumberOperations;
    @Autowired
    private CommonSteps commonSteps;

    @Before
    public void setUp() {
        projectCucumberOperations = new ProjectStepsImplementation(mockMvc);
        commonSteps.commonStepsImplementation = projectCucumberOperations;
    }

    @When("the client wants to receive all the projects")
    public void theClientWantsToReceiveAllProjects() throws Exception {
        projectCucumberOperations.performGetProjects();
    }

    @When("^the client wants to receive project by id = (\\d+)$")
    public void theClientWantsToReceiveProjectById(Long id) throws Exception {
        projectCucumberOperations.performGetProjectById(id);
    }

    @When("the client wants to create project with following data")
    public void theClientWantsToCreateProjectWithFollowingData(DataTable dataTable) throws Exception {
        projectCucumberOperations.performCreateProject(dataTable);
    }

    @And("the client receives newly created Project")
    public void theClientReceivesNewlyCreatedProject(DataTable dataTable) throws Exception {
        projectCucumberOperations.performReceiveCreatedProject(dataTable);
    }

    @Given("the client wants to edit project with id = {long}")
    public void theClientWantsToEditProjectWithId(Long id) {
        this.id = id;
    }

    @When("the client sets following project values")
    public void theClientSetsFollowingValues(DataTable dataTable) throws Exception {
        projectCucumberOperations.performEditProject(dataTable, id);
    }

    @And("the client receives edited Project")
    public void theClientReceivesEditedProject(DataTable dataTable) throws Exception {
        projectCucumberOperations.performReceiveEditedProject(dataTable);
    }

    @When("the client wants to check history of the project with id = {long}")
    public void theClientWantsToCheckHistoryOfTheProjectWithId(Long projectId) throws Exception {
        projectCucumberOperations.performGetProjectHistory(projectId);
    }

    @When("the client wants to check history details with id = {long} of the project with id = {long}")
    public void theClientWantsToCheckHistoryDetailsWithIdOfTheProjectWithId(Long historyId, Long projectId) throws Exception {
        projectCucumberOperations.performGetProjectHistoryDetails(projectId, historyId);
    }

    @And("the client sees project history details")
    public void theClientSeesProjectHistoryDetails(DataTable dataTable) throws Exception {
        projectCucumberOperations.performReceiveProjectHistoryDetails(dataTable);
    }
}
