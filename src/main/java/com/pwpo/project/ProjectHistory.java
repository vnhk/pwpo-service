package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.BaseHistoryEntity;
import com.pwpo.common.model.Constants;
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
    private String summary;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    private String name;
    private String shortForm;
    private String owner;
    @ManyToOne
    @JsonIgnore
    private Project project;
}