package com.pwpo.common.service;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.AttachmentHandler;
import com.pwpo.common.model.db.Attachment;
import com.pwpo.common.model.dto.AttachmentDTO;
import com.pwpo.common.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final ItemMapper mapper;
    @Value("${attachment.service.file.storage.url}")
    private String FOLDER;

    public UrlResource download(Long holderId, Long attachmentId) throws IOException {
        Path file = getFile(holderId, attachmentId);
        return new UrlResource(file.toUri());
    }


    public APIResponse upload(MultipartFile file, Long holderId) throws IOException {
        Optional<Attachment> byNameAndEntityId = attachmentRepository.findByNameAndEntityId(file.getOriginalFilename(), holderId);
        if (byNameAndEntityId.isPresent()) {
            throw new RuntimeException("Attachment with given name already exists!");
        }
        Attachment attachment = new Attachment();
        AttachmentHandler entity = new AttachmentHandler();
        entity.setId(holderId);
        attachment.setEntity(entity);
        attachment.setName(file.getOriginalFilename());
        attachment = attachmentRepository.save(attachment);
        store(file, holderId, attachment.getId());

        return mapper.mapToAPIResponse(attachment, AttachmentDTO.class);
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
        return getDestinationDir(holderId, attachmentId) + File.separator + filename;
    }

    private String getDestinationDir(Long holderId, Long attachmentId) {
        return FOLDER + File.separator + holderId + File.separator + attachmentId;
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
        //files are saved in directory with attachmentId as a name, so it should not be any files with the same name there
        //so fileExist wont be true
        while (fileExist) {
            tempFileName = fileName.substring(0, fileName.indexOf(extension) - 1) + "(" + i++ + ")." + extension;
            fileExist = isFileWithTheName(tempFileName, holderId, attachmentId);
        }

        return tempFileName;
    }

    private boolean isFileWithTheName(String fileName, Long holderId, Long attachmentId) {
        return new File(getDestination(holderId, attachmentId, fileName)).exists();
    }

    public APIResponse getAllAttachments(Long holderId) {
        List<ItemDTO> items = new ArrayList<>();
        List<Attachment> attachments = attachmentRepository.findByEntityId(holderId);
        for (Attachment attachment : attachments) {
            ItemDTO itemDTO = mapper.mapToDTO(attachment, AttachmentDTO.class);
            items.add(itemDTO);
        }
        return new APIResponse(items, items.size(), 0, items.size());
    }

    public Attachment getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId).get();
    }

    public void delete(Long attachmentId, Long holderId) {
        Optional<Attachment> byId = attachmentRepository.findById(attachmentId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Given attachment does not exist!");
        }
        String destination = getDestination(holderId, attachmentId, byId.get().getName());
        String destinationDir = getDestinationDir(holderId, attachmentId);
        File file = new File(destination);
        File dir = new File(destinationDir);
        if (!file.delete() || !dir.delete()) {
            throw new RuntimeException("File cannot be deleted!");
        }

        attachmentRepository.deleteById(attachmentId);
    }
}
