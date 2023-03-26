package com.pwpo.task.controller;

import com.pwpo.common.exception.NotFoundException;
import com.pwpo.task.TagRepository;
import com.pwpo.task.dto.TagDTO;
import com.pwpo.task.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/tags")
@CrossOrigin(origins = "http://localhost:4200")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping(path = "/tag")
    public ResponseEntity<TagDTO> getTagByName(String name) {

        Optional<Tag> tag = tagRepository.findByName(name);
        if (tag.isEmpty()) {
            throw new NotFoundException("Could not find tag!");
        }

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.get().getId());
        tagDTO.setName(tag.get().getName());
        tagDTO.setSummary(tag.get().getSummary());


        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('MANAGER')")
    public ResponseEntity saveTag(@Valid @RequestBody TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        tag.setSummary(tagDTO.getSummary());

        Optional<Tag> byName = tagRepository.findByName(tag.getName());
        if (byName.isPresent()) {
            byName.get().setSummary(tag.getSummary());
            tagRepository.save(byName.get());
        } else {
            tagRepository.save(tag);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
