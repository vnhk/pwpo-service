package com.pwpo.common.controller;

import com.pwpo.common.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/attachments/{holderId}/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping(path = "/attachment/{id}/download/")
    public void downloadAttachment(@PathVariable Long id, @PathVariable Long holderId, HttpServletResponse response) throws IOException {
        response.addHeader("Accept-Ranges", "bytes");
        attachmentService.download(holderId, id, response);
    }

    //    @PreAuthorize()
// find on db entity with this id and check if project or task and if has write access
    @PostMapping(path = "/attachment/")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long holderId) throws IOException {
        attachmentService.upload(file, holderId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
