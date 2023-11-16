package com.SpringSecurity.Samer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private Roles role;



}
