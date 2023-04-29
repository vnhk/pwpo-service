package com.pwpo.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwpo.common.model.db.Attachment;
import com.pwpo.common.model.db.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class AttachmentHandler extends BaseEntity {
    @OneToMany(mappedBy = "entity")
    @JsonIgnore
    private List<Attachment> attachments;
}
