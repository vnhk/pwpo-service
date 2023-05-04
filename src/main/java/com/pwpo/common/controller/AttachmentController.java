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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/attachments/{holderId}")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping(path = "/attachment/{id}/download")
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
    public ResponseEntity<APIResponse> getAll(@PathVariable Long holderId) throws IOException {
        return new ResponseEntity(attachmentService.getAllAttachments(holderId), HttpStatus.OK);
    }

    //    @PreAuthorize()
// find on db entity with this id and check if project or task and if has write access
    @PostMapping(path = "/attachment")
    public ResponseEntity<APIResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long holderId) throws IOException {
        return new ResponseEntity(attachmentService.upload(file, holderId), HttpStatus.OK);
    }

}
