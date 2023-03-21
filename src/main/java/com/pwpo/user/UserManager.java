package com.pwpo.user;

import com.pwpo.common.exception.ValidationException;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.common.search.model.SortDirection;
import com.pwpo.common.service.ItemMapper;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import com.pwpo.user.dto.UserDTO;
import com.pwpo.user.dto.UserWithRolesDTO;
import com.pwpo.user.model.UserProject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager {
    private final ItemMapper mapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;

    public APIResponse getUsersAddedToProject(Long id, SearchQueryOption options) {
        PageRequest of = PageRequest.of(options.getPage() - 1, options.getPageSize(), getSortBy(options));

        Page<UserAccount> users = userRepository.findUsersAddedToTheProject(id, of);
        List<ItemDTO> result = new ArrayList<>();
        for (UserAccount user : users) {
            UserDTO itemDTO = (UserDTO) mapper.mapToDTO(user, UserDTO.class);
            ProjectRole projectRole = user.getAddedToProjects().stream().filter(e -> e.getProject().getId().equals(id)).map(UserProject::getRole)
                    .findFirst().get();
            itemDTO.setProjectRole(projectRole.getDisplayName());

            result.add(itemDTO);
        }

        return new APIResponse(result, result.size(), options.getPage(), Math.toIntExact(users.getTotalElements()));
    }

    private Sort getSortBy(SearchQueryOption options) {
        String sortField = camelToSnake(options.getSortField());
        if (options.getSortDirection().equals(SortDirection.ASC)) {
            return Sort.by(sortField).ascending();
        } else {
            return Sort.by(sortField).descending();
        }
    }

    private String camelToSnake(String str) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return str.replaceAll(regex, replacement).toUpperCase();
    }

    public APIResponse getUsersNotAddedToProject(Long id) {
        List<UserAccount> addedUsers = userRepository.findUsersAddedToTheProject(id);
        List<UserAccount> allUsers = userRepository.findAll();
        List<Long> addedUsersId = addedUsers.stream().map(u -> u.getId()).toList();

        List<UserAccount> notAddedUsers = allUsers.stream().filter(e -> !addedUsersId.contains(e.getId()))
                .toList();

        List<ItemDTO> collect = notAddedUsers.stream()
                .map(user -> mapper.mapToDTO(user, UserDTO.class))
                .collect(Collectors.toList());

        return new APIResponse(collect, collect.size(), 1, collect.size());
    }

    public APIResponse getUsers() {
        List<UserAccount> users = userRepository.findAll();
        List<ItemDTO> collect = users.stream()
                .map(user -> mapper.mapToDTO(user, UserDTO.class))
                .collect(Collectors.toList());

        return new APIResponse(collect, collect.size(), 1, collect.size());
    }

    public APIResponse getUsersWithRoles() {
        List<UserAccount> users = userRepository.findAll();
        List<ItemDTO> collect = users.stream()
                .map(user -> mapper.mapToDTO(user, UserWithRolesDTO.class))
                .collect(Collectors.toList());

        return new APIResponse(collect, collect.size(), 1, collect.size());
    }

    public void addUserToTheProject(Long projectId, Long userId, ProjectRole projectRole) {
        Optional<UserAccount> userOpt = userRepository.findById(userId);
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        validate(projectOpt, userOpt);

        UserAccount user = userOpt.get();
        Project project = projectOpt.get();

        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setRole(projectRole);

        userProjectRepository.save(userProject);
    }

    private void validate(Optional<Project> project, Optional<UserAccount> user) {
        if (user.isEmpty()) {
            throw new ValidationException("User does not exist!");
        }

        if (project.isEmpty()) {
            throw new ValidationException("Project does not exist!");
        }

        if (userRepository.findUsersAddedToTheProject(project.get().getId()).stream()
                .anyMatch(u -> u.getId().equals(user.get().getId()))) {
            throw new ValidationException("User has been already added to the project!");
        }
    }

    public void editUser(UserDTO userDTO) {
        Optional<UserAccount> byId = userRepository.findById(userDTO.getId());

        if (byId.isEmpty()) {
            throw new ValidationException("User does not exist!");
        }

        UserAccount userAccount = byId.get();
        userAccount.setEmail(userDTO.getEmail());
        userAccount.setFirstName(userDTO.getFirstName());
        userAccount.setLastName(userDTO.getLastName());

        userRepository.edit(userAccount);
    }
}
