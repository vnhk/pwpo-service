package com.pwpo.project;

import com.pwpo.common.ItemMapper;
import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectManager {
    private final ItemMapper mapper;
    private final ProjectRepository projectRepository;

    public APICollectionResponse getProjects(Class<? extends ItemDTO> dtoClass) {
        Iterable<Project> all = projectRepository.findAll();
        List<ItemDTO> collect = StreamSupport.stream(all.spliterator(), false)
                .map(project -> mapper.mapToDTO(project, dtoClass))
                .collect(Collectors.toList());

        return new APICollectionResponse(collect, collect.size());
    }

    public ItemDTO getProjectById(String id, Class<? extends ItemDTO> dtoClass) {
        Optional<Project> project = projectRepository.findById(Long.parseLong(id));
        if (project.isPresent()) {
            project.get().setDescription("newDesc" + new Date());
            return mapper.mapToDTO(project.get(), dtoClass);
        } else {
            throw new RuntimeException("Could not find project!");
        }
    }
}
