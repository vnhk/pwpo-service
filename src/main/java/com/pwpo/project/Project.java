package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.user.model.Constants;
import com.pwpo.common.enums.Status;
import com.pwpo.user.model.Itemable;
import com.pwpo.user.model.UserProject;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.common.serializer.ToNickNameSerializer;
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
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    @Column(unique = true)
    private String shortForm;
    @ManyToOne
    private UserDetails owner;
    @ManyToOne
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
