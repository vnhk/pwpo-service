package com.pwpo.project.dto;

import com.pwpo.common.enums.Status;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.project.ProjectHistory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EditProjectRequestDTO extends ProjectRequestDTO implements Editable<Long> {
    @NotNull
    private Long id;
    @NotNull
    private Status status;

    @Override
    public Long getEntityId() {
        return  id;
    }

    @Override
    public Class<? extends BaseHistoryEntity> getHistoryClass() {
        return ProjectHistory.class;
    }
}
