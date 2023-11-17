package com.SpringSecurity.Samer.controller;

import com.SpringSecurity.Samer.model.AuthRequest;
import com.SpringSecurity.Samer.model.Roles;
import com.SpringSecurity.Samer.model.UserEntity;
import com.SpringSecurity.Samer.model.UserToShowToSwagger;
import com.SpringSecurity.Samer.service.JWTService;
import com.SpringSecurity.Samer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private JWTService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserToShowToSwagger user) {


        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        } else {

            UserEntity newUser = new UserEntity();
            newUser.setUsername(user.getUsername());
            newUser.setRole(Roles.ROLE_USER);
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));

            userService.save(newUser);
            return ResponseEntity.status(HttpStatus.OK).body("Registration successful!");
        }
    }


    @PostMapping("/login")
    public String authAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password").toString();
        }

    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{name}")
    public ResponseEntity<UserEntity> getUserByName(@PathVariable String name) {
        UserEntity user = userService.findByUsername(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        if (userService.findAll() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        Optional<UserEntity> result = userService.updateUserById(id, updatedUser);

        if (result.isPresent()) {
            return new ResponseEntity<>("Done!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }
}
