package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.Constants;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Itemable;
import com.pwpo.common.model.UserProject;
import com.pwpo.common.serializer.ToFullNameSerializer;
import com.pwpo.task.Task;
import com.pwpo.user.UserDetails;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Itemable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String summary;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    private String shortForm;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails owner;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails createdBy;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean isDeleted;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<UserProject> addedToProjects;
}
