package com.SpringSecurity.Samer.service;

import com.SpringSecurity.Samer.model.SecurityUser;
import com.SpringSecurity.Samer.model.UserEntity;
import com.SpringSecurity.Samer.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    public JpaUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            return new SecurityUser(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
