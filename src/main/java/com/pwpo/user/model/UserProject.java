package com.pwpo.user.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.project.model.Project;
import com.pwpo.user.ProjectRole;
import com.pwpo.user.UserAccount;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserProject extends BaseEntity {
    @ManyToOne
    private UserAccount user;
    @ManyToOne
    private Project project;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private ProjectRole role;
}
