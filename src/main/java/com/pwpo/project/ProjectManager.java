package com.pwpo.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.APICollectionResponse;
import com.pwpo.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectManager {
    private final ObjectMapper objectMapper;
    private final ProjectRepository projectRepository;

    public APICollectionResponse getProjects(Class<? extends ItemDTO> dtoClass) {
        Iterable<Project> all = projectRepository.findAll();
        List<ItemDTO> collect = StreamSupport.stream(all.spliterator(), false)
                .map(project -> mapToDTO(project, dtoClass))
                .collect(Collectors.toList());

        return new APICollectionResponse(collect, collect.size());
    }

    public ItemDTO getProjectById(String id, Class<? extends ItemDTO> dtoClass) {
        Optional<Project> project = projectRepository.findById(Long.parseLong(id));

        if (project.isPresent()) {
            return mapToDTO(project.get(), dtoClass);
        } else {
            throw new RuntimeException("Could not find project!");
        }
    }


    private ItemDTO mapToDTO(Project project, Class<? extends ItemDTO> dtoClass) {
        try {
            String projectAsString = objectMapper.writeValueAsString(project);
            return objectMapper.readValue(projectAsString, dtoClass);
        } catch (JsonProcessingException e) {
            log.error("Could not map project!", e);
            throw new RuntimeException("Could not map project!");
        }
    }
}
