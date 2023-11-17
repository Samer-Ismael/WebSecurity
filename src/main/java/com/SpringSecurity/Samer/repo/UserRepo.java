package com.SpringSecurity.Samer.repo;

import com.SpringSecurity.Samer.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsById(Long id);

    Optional<UserEntity> findByUsername(String username);

}
