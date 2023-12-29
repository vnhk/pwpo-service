package com.pwpo.common.controller;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.service.EnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/enums")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}
        , exposedHeaders = "*")
public class EnumController {
    private final EnumService enumService;

    @GetMapping(path = "/enum")
    public ResponseEntity<APIResponse> getEnum(@RequestParam String name) {
        return new ResponseEntity<>(enumService.getEnumByName(name), HttpStatus.OK);
    }
}
