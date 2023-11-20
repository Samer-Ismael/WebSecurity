package com.SpringSecurity.Samer.controller;

import com.SpringSecurity.Samer.model.AuthRequest;
import com.SpringSecurity.Samer.model.ChangingPassword;
import com.SpringSecurity.Samer.model.Roles;
import com.SpringSecurity.Samer.model.UserEntity;
import com.SpringSecurity.Samer.service.JWTService;
import com.SpringSecurity.Samer.filter.PasswordValidator;
import com.SpringSecurity.Samer.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

// UserController is a REST controller that handles user-related requests.
// It is annotated with @RestController to indicate that it's a controller where every method returns a domain object instead of a view.
// It's also annotated with @RequestMapping("/users") to map web requests onto specific handler classes and/or handler methods.
@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private JWTService jwtService;

    // Constructor for UserController, it takes an AuthenticationManager, UserService, PasswordEncoder, and JWTService as parameters.
    public UserController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // The register method handles the registration of new users.
    // It checks if the username already exists, if not, it creates a new user with the provided details.
    @ApiOperation(value = "Register", notes = "Everybody is welcome to register")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest user) {

        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        } else {

            String badPass = PasswordValidator.validatePassword(user.getPassword());
            if (!badPass.equals("")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badPass);
            }
            UserEntity newUser = new UserEntity();
            newUser.setUsername(user.getUsername());
            newUser.setRole(Roles.ROLE_USER);
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));

            userService.save(newUser);
            return ResponseEntity.status(HttpStatus.OK).body("Registration successful!");
        }
    }

    // The authAndGetToken method handles the authentication of users.
    // It authenticates the user and if successful, generates a JWT token for the user.
    // Here you can ask for payment or something else before generating the token.
    @ApiOperation(value = "getToken", notes = "Returns a token if the user is authenticated")
    @PostMapping("/getToken")
    public ResponseEntity<String> authAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                return new ResponseEntity<>(jwtService.generateToken(authRequest.getUsername()), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
    }

    // The getUserByName method retrieves a user by their username.
    // It returns a ResponseEntity containing the UserEntity if found, or a NOT_FOUND status otherwise.
    @ApiOperation(value = "Get user by id", notes = "Returns user by id (for admins and users)")
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

    // The getAllUsers method retrieves all users.
    // It returns a ResponseEntity containing a list of all UserEntity objects, or a NOT_FOUND status if no users are found.
    @ApiOperation(value = "Get all users", notes = "Returns all users in the database (for admins and users)")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        if (userService.findAll() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }
    }

    // The deleteUser method deletes a user by their id.
    // It returns a ResponseEntity with an OK status if the user was successfully deleted, or a NOT_FOUND status otherwise.
    @ApiOperation(value = "Delete user", notes = "Deletes user by id (for admins only))")
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

    // The updateUserById method updates a user by their id.
    // It returns a ResponseEntity with an OK status and a message if the user was successfully updated, or a NOT_FOUND status otherwise.
    @ApiOperation(value = "Update user", notes = "Updates user by id (for admins only))")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        if (updatedUser.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (userService.existsById(id)) {
            UserEntity newUser = userService.findById(id).get();
            if (updatedUser.getPassword() == null) updatedUser.setPassword(newUser.getPassword());
            if (updatedUser.getRole() == null) updatedUser.setRole(newUser.getRole());
            if (updatedUser.getUsername() == null) updatedUser.setUsername(newUser.getUsername());

            userService.updateUserById(id, updatedUser);
            return new ResponseEntity<>("Done!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }

    // The deleteUser method deletes the currently logged-in user.
    // It returns a ResponseEntity with an OK status and a message if the user was successfully deleted, or a NOT_FOUND status otherwise.
    @ApiOperation(value = "Delete user", notes = "Deletes the user that is logged in, not other users ")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(Principal principal) {
        String username = principal.getName();
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            userService.deleteById(user.getId());
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // The updateUser method updates the currently logged-in user.
    // It returns a ResponseEntity with an OK status and a message if the user was successfully updated,
    // or a NOT_FOUND status otherwise.
    @ApiOperation(value = "Delete user", notes = "Change the pass for the user that is logged in, not other users ")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/changePass")
    public ResponseEntity<String> changeUserPass (Principal principal, @RequestBody ChangingPassword changingPassword) {
        UserEntity user = userService.findByUsername(principal.getName());
        String oldPass = changingPassword.getOldPassword();
        String newPass = changingPassword.getNewPassword();
        String confirmPass = changingPassword.getConfirmPassword();

        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            if (newPass.equals(confirmPass)) {
                String badPass = PasswordValidator.validatePassword(newPass);
                if (!badPass.equals("")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badPass);
                }
                user.setPassword(passwordEncoder.encode(newPass));
                userService.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirm password do not match");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }




    }

}