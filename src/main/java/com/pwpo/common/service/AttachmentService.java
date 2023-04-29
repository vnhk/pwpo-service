package com.pwpo.common.service;

import com.pwpo.common.model.AttachmentHandler;
import com.pwpo.common.model.db.Attachment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    @Value("${attachment.service.file.storage.url}")
    private String FOLDER;

    public void download(Long holderId, Long attachmentId, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        Path file = getFile(holderId, attachmentId);
        Files.copy(file, outputStream);
    }

    public void upload(MultipartFile file, Long holderId) throws IOException {
        Optional<Attachment> byNameAndEntityId = attachmentRepository.findByNameAndEntityId(file.getOriginalFilename(), holderId);
        if (byNameAndEntityId.isPresent()) {
            throw new RuntimeException("Attachment with given name already exists!");
        }
        Attachment attachment = new Attachment();
        AttachmentHandler entity = new AttachmentHandler();
        entity.setId(holderId);
        attachment.setEntity(entity);
        attachment = attachmentRepository.save(attachment);
        store(file, holderId, attachment.getId());
    }

    private Path getFile(Long holderId, Long attachmentId) {
        try {
            Attachment attachment = attachmentRepository.findById(attachmentId).get();
            String filename = attachment.getName();
            File file = new File(getDestination(holderId, attachmentId, filename));
            return Paths.get(file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Cannot get file " + attachmentId);
        }
    }

    private String getDestination(Long holderId, Long attachmentId, String filename) {
        return FOLDER + File.separator + holderId + File.separator + attachmentId + File.separator + filename;
    }

    private String store(MultipartFile file, Long holderId, Long attachmentId) {
        String fileName = getFileName(file.getOriginalFilename(), holderId, attachmentId);
        try {
            String destination = getDestination(holderId, attachmentId, fileName);
            File fileTmp = new File(destination);
            File directory = new File(FOLDER + File.separator + holderId + File.separator + attachmentId + File.separator);
            directory.mkdirs();
            Files.copy(file.getInputStream(), fileTmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return fileName;
    }

    private String getFileName(String fileName, Long holderId, Long attachmentId) {
        String extension = FilenameUtils.getExtension(fileName);
        String tempFileName = fileName;
        boolean fileExist = isFileWithTheName(fileName, holderId, attachmentId);
        int i = 1;
        while (fileExist) {
            tempFileName = fileName.substring(0, fileName.indexOf(extension) - 1) + "(" + i++ + ")." + extension;
            fileExist = isFileWithTheName(tempFileName, holderId, attachmentId);
        }

        return tempFileName;
    }

    private boolean isFileWithTheName(String fileName, Long holderId, Long attachmentId) {
        return new File(getDestination(holderId, attachmentId, fileName)).exists();
    }
}
