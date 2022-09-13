package com.pwpo.user;

import com.pwpo.project.Project;
import com.pwpo.task.Task;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nick;
    private String fullName;

    @OneToMany(mappedBy = "owner")
    private List<Project> ownedProjects;
    @OneToMany(mappedBy = "createdBy")
    private List<Project> createdProjects;

    @OneToMany(mappedBy = "owner")
    private List<Task> ownedTasks;
    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;
    @OneToMany(mappedBy = "assignee")
    private List<Task> assignedTasks;
}
