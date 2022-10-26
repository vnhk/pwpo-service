package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.deserializer.FromUserIdDeserializer;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.task.Task;
import com.pwpo.user.UserDetails;
import com.pwpo.user.model.UserProject;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Project extends BaseEntity {
    private String summary;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String shortForm;
    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    private UserDetails owner;
    @ManyToOne
    private UserDetails createdBy;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<UserProject> addedToProjects;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<ProjectHistory> history;

    @Override
    public List getHistoryEntities() {
        return history;
    }
}
