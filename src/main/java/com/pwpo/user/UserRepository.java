package com.pwpo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    @Query(value = "SELECT ud.* FROM User_Details as ud, User_Project as up WHERE ud.id = up.user_id AND up.project_id = ?1",
            nativeQuery = true,
            countQuery = "SELECT count(ud.*) FROM User_Details as ud, User_Project as up WHERE ud.id = up.user_id AND up.project_id = ?1")
    Page<UserDetails> findUsersAddedToTheProject(Long projectId, Pageable pageable);

    @Query("SELECT ud FROM UserDetails as ud, UserProject as up WHERE ud.id = up.user.id " +
            "AND up.project.id = ?1 AND ud.updated = -1 AND up.updated = -1")
    List<UserDetails> findUsersAddedToTheProject(Long projectId);
}
