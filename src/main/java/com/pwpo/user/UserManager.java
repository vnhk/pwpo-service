package com.pwpo.user;

import com.pwpo.common.ItemMapper;
import com.pwpo.common.model.APICollectionResponse;
import com.pwpo.common.model.ItemDTO;
import com.pwpo.project.Project;
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
public class UserManager {
    private final ItemMapper mapper;
    private final UserRepository userRepository;

    public APICollectionResponse getUsersAddedToProject(String id, Class<? extends ItemDTO> dtoClass) {
        List<UserDetails> users = userRepository.findUsersAddedToTheProject(Long.valueOf(id));
        List<ItemDTO> collect = users.stream()
                .map(user -> mapper.mapToDTO(user, dtoClass))
                .collect(Collectors.toList());

        return new APICollectionResponse(collect, collect.size());
    }
}
