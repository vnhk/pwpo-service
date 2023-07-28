package com.pwpo.security;

import com.pwpo.common.exception.ValidationException;
import com.pwpo.user.AccountRole;
import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import com.pwpo.user.dto.UserWithRolesDTO;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    protected final Log logger = LogFactory.getLog(getClass());

    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    final PermissionEvaluator permissionEvaluator;

    public AuthenticationController(UserRepository userRepository, AuthenticationManager authenticationManager,
                                    JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
                                    PermissionEvaluator permissionEvaluator) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.permissionEvaluator = permissionEvaluator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

                if (!permissionEvaluator.isNotDisabled(userDetails.getAuthorities())) {
                    throw new DisabledException("Disabled user.");
                }

                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                responseMap.put("expiresIn", jwtTokenUtil.getExpirationDateFromToken(token));
                responseMap.put("username", userDetails.getUsername());
                responseMap.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority));
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            responseMap.put("error", true);
            responseMap.put("message", "User is disabled");
            return ResponseEntity.status(401).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Invalid Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @PostMapping("/reset-password")
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('ADMIN')")
    public ResponseEntity<?> saveUser(@RequestBody UserWithRolesDTO request) {
        Map<String, Object> responseMap = new HashMap<>();
        String password = generatePassword();

        String nick = request.getNick();
        UserAccount userAccount = userRepository.findByNick(nick).get();
        userAccount.setPassword(new BCryptPasswordEncoder().encode(password));
        userAccount.getRoles().add(AccountRole.ROLE_NOT_ACTIVATED);
        String token = jwtTokenUtil.generateToken(userAccount);
        userRepository.saveWithoutHistory(userAccount);
        responseMap.put("username", nick);
        responseMap.put("message", "Password regenerated, change password after first login!");
        responseMap.put("token", token);
        responseMap.put("password", password);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/register")
    @PreAuthorize("@permissionEvaluator.activatedAndHasRole('ADMIN')")
    public ResponseEntity<?> saveUser(@RequestBody AuthRequest authRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        UserAccount user = new UserAccount();
        String firstName = authRequest.getFirstName();
        String lastName = authRequest.getLastName();
        String email = authRequest.getEmail();
        String password = generatePassword();
        String userName = authRequest.getUsername();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.getRoles().add(authRequest.getRole());
        user.getRoles().add(AccountRole.ROLE_NOT_ACTIVATED);
        user.setNick(userName);

        validateRegister(user);

        String token = jwtTokenUtil.generateToken(user);
        userRepository.save(user);
        responseMap.put("username", userName);
        responseMap.put("message", "Account created successfully, change password after first login!");
        responseMap.put("token", token);
        responseMap.put("password", password);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changeResetPassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        UserAccount loggedUserData = userRepository.findByNick(username).get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedNewPassword = bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword());

        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), loggedUserData.getPassword())) {
            throw new ValidationException("password", "Old password is not correct!");
        }

        if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
            throw new ValidationException("password", "New password must be different!");
        }

        loggedUserData.getRoles().remove(AccountRole.ROLE_NOT_ACTIVATED);
        loggedUserData.setPassword(encodedNewPassword);

        userRepository.saveWithoutHistory(loggedUserData);

        responseMap.put("error", false);
        responseMap.put("username", username);
        responseMap.put("message", "Password changed successfully!");

        return ResponseEntity.ok(responseMap);
    }

    private String generatePassword() {
        return RandomString.make(10);
    }


    private void validateRegister(UserAccount user) {
        if (user.getRoles().size() < 2) {
            throw new ValidationException("role", "Role is required!");
        }

        Optional<UserAccount> byNick = userRepository.findByNick(user.getNick());

        if (byNick.isPresent()) {
            throw new ValidationException("username", "User with given nick already exists!");
        }

        Optional<UserAccount> byEmail = userRepository.findByEmail(user.getEmail());

        if (byEmail.isPresent()) {
            throw new ValidationException("email", "User with given email already exists!");
        }
    }
}