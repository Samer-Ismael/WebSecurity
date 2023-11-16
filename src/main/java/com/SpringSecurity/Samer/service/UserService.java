package com.SpringSecurity.Samer.service;

import com.SpringSecurity.Samer.model.Roles;
import com.SpringSecurity.Samer.model.UserEntity;
import com.SpringSecurity.Samer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<String> save (UserEntity user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        } else {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setRole(Roles.ROLE_USER);
            userRepo.save(newUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

   public List<UserEntity> findAll () {
        if (userRepo.findAll().isEmpty()) {
            return null;
        }else {
            userRepo.findAll();
            return new ArrayList<>(userRepo.findAll());
        }
    }


    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public Optional<UserEntity> updateUserById(Long userId, UserEntity updatedUser) {
        Optional<UserEntity> existingUser = userRepo.findById(userId);

        if (existingUser.isPresent()) {
            UserEntity userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setPassword(updatedUser.getPassword());
            userToUpdate.setRole(updatedUser.getRole());

            return Optional.of(userRepo.save(userToUpdate));
        } else {
            return Optional.empty();
        }
    }

    public void deleteById (Long id) {
        userRepo.deleteById(id);
    }

    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public Boolean existsById(Long id) {
        return userRepo.existsById(id);
    }

}
