package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.HistoryField;
import com.pwpo.common.model.db.BaseEntity;
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
    @HistoryField
    private String summary;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    @HistoryField
    private String description;
    @HistoryField
    private String name;
    @HistoryField
    private String shortForm;
    @HistoryField(path = "nick")
    private String owner;
    @ManyToOne
    @JsonIgnore
    @HistoryField(comparable = false, isTargetEntity = true)
    private Project project;

    @Override
    public void buildTargetEntityConnection(BaseEntity entity) {
        this.project = (Project) entity;
        project.getHistoryEntities().add(this);
    }
}