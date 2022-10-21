package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Comparable;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProjectHistory extends BaseHistoryEntity {
    @Comparable
    private String summary;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @Comparable
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    @Comparable
    private String description;
    @Comparable
    private String name;
    @Comparable
    private String shortForm;
    @Comparable(path = "owner.nick")
    private String owner;
    @ManyToOne
    @JsonIgnore
    private Project project;
}