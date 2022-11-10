package com.pwpo.integration.stepsimpl;

import com.pwpo.TestUtils;
import com.pwpo.common.model.APIResponse;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.project.model.Project;
import io.cucumber.datatable.DataTable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectStepsImplementation extends CommonStepsImplementation {

    public ProjectStepsImplementation(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public void performGetProjects() throws Exception {
        String params = getDefaultGetParams();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        apiResponse = TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);
    }

    public void performGetProjectById(Long projectId) throws Exception {
        String params = "?id=" + projectId + "&dto=" + ProjectPrimaryResponseDTO.class.getName();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/project" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        apiResponse = TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);
    }

    public void performCreateProject(DataTable dataTable) throws Exception {
        ProjectRequestDTO req = buildDTO(dataTable, ProjectRequestDTO.class);

        mvcResult = mockMvc.perform(TestUtils.buildPostRequest("/projects", req, mapper))
                .andReturn();
    }

    public void performEditProject(DataTable dataTable, Long id) throws Exception {
        EditProjectRequestDTO req = buildDTO(dataTable, EditProjectRequestDTO.class);
        req.setId(id);

        mvcResult = mockMvc.perform(TestUtils.buildPutRequest("/projects", req, mapper))
                .andReturn();
    }

    public void performReceiveEditedProject(DataTable dataTable) throws Exception {
        APIResponse<ProjectPrimaryResponseDTO> editedProjectRes
                = TestUtils.convertAPIResponse(mvcResult.getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        ProjectPrimaryResponseDTO edited = editedProjectRes.getItems().get(0);

        assertThat(Long.parseLong(data.get("id"))).isEqualTo(edited.getId());
        assertThat(data.get("name")).isEqualTo(edited.getName());
        assertThat(Long.parseLong(data.get("owner"))).isEqualTo(edited.getOwner().getId());
        assertThat(data.get("status")).isEqualTo(edited.getStatus());
        assertThat(data.get("summary")).isEqualTo(edited.getSummary());
        assertThat(data.get("shortForm")).isEqualTo(edited.getShortForm());
    }

    public void performReceiveCreatedProject(DataTable dataTable) throws Exception {
        APIResponse<ProjectPrimaryResponseDTO> createdProjectRes
                = TestUtils.convertAPIResponse(mvcResult.getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        ProjectPrimaryResponseDTO createdProject = createdProjectRes.getItems().get(0);

        assertThat(data.get("name")).isEqualTo(createdProject.getName());
        assertThat(Long.parseLong(data.get("owner"))).isEqualTo(createdProject.getOwner().getId());
        assertThat(data.get("status")).isEqualTo(createdProject.getStatus());
        assertThat(data.get("summary")).isEqualTo(createdProject.getSummary());
        assertThat(data.get("shortForm")).isEqualTo(createdProject.getShortForm());
    }

    private String getDefaultGetParams() {
        return super.getDefaultGetParams(Project.class);
    }
}
