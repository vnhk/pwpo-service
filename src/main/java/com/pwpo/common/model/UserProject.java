package com.pwpo.common.model;

import com.pwpo.project.Project;
import com.pwpo.user.ProjectRole;
import com.pwpo.user.UserDetails;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserDetails user;
    @ManyToOne
    private Project project;
    @Enumerated(EnumType.STRING)
    private ProjectRole role;
}
