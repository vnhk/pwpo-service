package com.pwpo.common.service;

import com.pwpo.common.model.db.Attachment;

import java.util.List;
import java.util.Optional;


public interface AttachmentRepository extends PwpoBaseRepository<Attachment, Long> {
    Optional<Attachment> findByNameAndEntityId(String originalFilename, Long holderId);

    List<Attachment> findByEntityId(Long holderId);
}
