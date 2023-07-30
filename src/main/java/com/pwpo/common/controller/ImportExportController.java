package com.pwpo.common.controller;

import com.pwpo.common.service.IEDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/ie-entities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("@permissionEvaluator.activatedAndHasRole('SUPER_ADMIN')")
public class ImportExportController {
    private final IEDataService ieDataService;

    @GetMapping(path = "/export")
    public ResponseEntity<Resource> exportData() throws IOException {
        // TODO: 30/07/2023 Think about deleting generated xlsx file
        Resource resource = ieDataService.export();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(path = "/import")
    public ResponseEntity importData(@RequestParam("file") MultipartFile file) throws IOException {
        ieDataService.importData(file);
        return new ResponseEntity(HttpStatus.OK);
    }

}
