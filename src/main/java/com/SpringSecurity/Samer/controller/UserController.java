package com.SpringSecurity.Samer.controller;

import com.SpringSecurity.Samer.model.UserEntity;
import com.SpringSecurity.Samer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String home() {
        return ("<div style='text-align: center;'><h2 style='font-size: 100px;'>Welcome</h2></div>");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser (@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        } else {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        Optional<UserEntity> result = userService.updateUserById(id, updatedUser);

        if (result.isPresent()) {
            return new ResponseEntity<>("Done!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers () {
        if (userService.findAll()==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }
    }
    @GetMapping("/{name}")
    public ResponseEntity<UserEntity> getUserByName(@PathVariable String name) {
        UserEntity user = userService.findByUsername(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser (@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
