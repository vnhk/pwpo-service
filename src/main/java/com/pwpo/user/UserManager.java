package com.pwpo.user;

import com.pwpo.user.model.APIResponse;
import com.pwpo.user.model.ItemDTO;
import com.pwpo.common.service.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager {
    private final ItemMapper mapper;
    private final UserRepository userRepository;

    public APIResponse getUsersAddedToProject(String id, Class<? extends ItemDTO> dtoClass) {
        List<UserDetails> users = userRepository.findUsersAddedToTheProject(Long.valueOf(id));
        List<ItemDTO> collect = users.stream()
                .map(user -> mapper.mapToDTO(user, dtoClass))
                .collect(Collectors.toList());

        return new APIResponse(collect, collect.size(), 1, collect.size());
    }
}
