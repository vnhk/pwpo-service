package com.pwpo.user;

import com.pwpo.common.model.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserManager userManager;

    @GetMapping
    public ResponseEntity<APIResponse> getUsers() {
        return new ResponseEntity<>(userManager.getUsers(), HttpStatus.OK);
    }
}
