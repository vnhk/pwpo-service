package com.pwpo.common.model.edit;

import com.pwpo.common.model.db.BaseHistoryEntity;

public interface Editable<ID> {
    ID getEntityId();

    Class<? extends BaseHistoryEntity> getHistoryClass();
}
