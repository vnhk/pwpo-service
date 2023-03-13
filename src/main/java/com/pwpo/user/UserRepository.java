package com.pwpo.user;

import com.pwpo.common.service.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends BaseRepository<UserAccount, Long> {
    @Query(value = "SELECT ud.* FROM User_Account as ud, User_Project as up WHERE ud.id = up.user_id AND up.project_id = ?1",
            nativeQuery = true,
            countQuery = "SELECT count(ud.*) FROM User_Account as ud, User_Project as up WHERE ud.id = up.user_id AND up.project_id = ?1")
    Page<UserAccount> findUsersAddedToTheProject(Long projectId, Pageable pageable);

    @Query("SELECT ud FROM UserAccount as ud, UserProject as up WHERE ud.id = up.user.id " +
            "AND up.project.id = ?1")
    List<UserAccount> findUsersAddedToTheProject(Long projectId);

    Optional<UserAccount> findByNick(String nick);
}
