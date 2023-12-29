package com.pwpo.common.controller;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.db.Attachment;
import com.pwpo.common.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/attachments/{holderId}")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping(path = "/attachment/{id}/download")
    @PreAuthorize("@permissionEvaluator.readAttachmentAccess(#holderId)")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id, @PathVariable Long holderId) throws IOException {
        Resource resource = attachmentService.download(holderId, id);
        Attachment attachment = attachmentService.getAttachment(id);
        String filename = attachment.getName();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping
    @PreAuthorize("@permissionEvaluator.readAttachmentAccess(#holderId)")
    public ResponseEntity<APIResponse> getAll(@PathVariable Long holderId) throws IOException {
        return new ResponseEntity(attachmentService.getAllAttachments(holderId), HttpStatus.OK);
    }

    @PreAuthorize("@permissionEvaluator.writeAttachmentAccess(#holderId)")
    @DeleteMapping(path = "/attachment/{id}")
    public ResponseEntity delete(@PathVariable Long id, @PathVariable Long holderId) throws IOException {
        attachmentService.delete(id, holderId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("@permissionEvaluator.writeAttachmentAccess(#holderId)")
    @PostMapping(path = "/attachment")
    public ResponseEntity<APIResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long holderId) throws IOException {
        return new ResponseEntity(attachmentService.upload(file, holderId), HttpStatus.OK);
    }

}
