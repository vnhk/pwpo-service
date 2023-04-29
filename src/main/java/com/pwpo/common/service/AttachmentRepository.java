package com.pwpo.common.service;

import com.pwpo.common.model.db.Attachment;

import java.util.Optional;


public interface AttachmentRepository extends BaseRepository<Attachment, Long> {
    Optional<Attachment> findByNameAndEntityId(String originalFilename, Long holderId);
}
