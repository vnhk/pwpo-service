package com.pwpo.integration.project;

import com.pwpo.TestUtils;
import com.pwpo.common.model.APIResponse;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ProjectCucumberOperations extends BaseCucumberOperations {
    public APIResponse performGetProjects(String params) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        return TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);
    }

    public APIResponse<ProjectPrimaryResponseDTO> getProjectById(Long projectId) throws Exception {
        String params = "?id=" + projectId + "&dto=" + ProjectPrimaryResponseDTO.class.getName();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/project" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        return TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);
    }

    public MvcResult createProject(ProjectRequestDTO req) throws Exception {
        return mockMvc.perform(TestUtils.buildPostRequest("/projects", req, mapper))
                .andReturn();
    }

    public MvcResult editProject(EditProjectRequestDTO req) throws Exception {
        return mockMvc.perform(TestUtils.buildPutRequest("/projects", req, mapper))
                .andReturn();
    }
}
