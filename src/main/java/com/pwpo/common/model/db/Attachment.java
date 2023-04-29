package com.pwpo.common.model.db;

import com.pwpo.common.model.AttachmentHandler;
import com.pwpo.common.model.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Attachment extends BaseEntity {
    @Size(min = 1, max = Constants.ATTACHMENT_NAME_MAX)
    @NotNull
    @Column(length = Constants.ATTACHMENT_NAME_MAX)
    private String name;

    @ManyToOne
    private AttachmentHandler entity;
}
