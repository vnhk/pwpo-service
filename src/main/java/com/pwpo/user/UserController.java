package com.pwpo.user;

import com.pwpo.common.model.APIResponse;
import com.pwpo.user.dto.UserDTO;
import com.pwpo.user.dto.UserWithRolesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserManager userManager;

    @GetMapping("/admin/users")
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('ADMIN')")
    public ResponseEntity<APIResponse> getUsersWithRoles() {
        return new ResponseEntity<>(userManager.getUsersWithRoles(), HttpStatus.OK);
    }

    @PutMapping("/admin/users")
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('ADMIN')")
    public ResponseEntity editUser(@RequestBody UserDTO userDTO) {
        userManager.editUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("@permissionEvaluator.activatedAndHasAnyRole()")
    public ResponseEntity<APIResponse> getUsers() {
        return new ResponseEntity<>(userManager.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/logged/details")
    public ResponseEntity<UserWithRolesDTO> getLoggedUserDetails() {
        return new ResponseEntity<>(userManager.getLoggedUserDetails(), HttpStatus.OK);
    }

    @PostMapping("/admin/users/disable/{id}")
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('ADMIN')")
    public ResponseEntity disable(@PathVariable Long id) {
        userManager.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/users/enable/{id}")
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('ADMIN')")
    public ResponseEntity enable(@PathVariable Long id) {
        userManager.enable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
