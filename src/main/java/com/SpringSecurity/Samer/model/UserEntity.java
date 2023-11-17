package com.SpringSecurity.Samer.model;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "username", example = "username")
    private String username;
    @ApiModelProperty(value = "password", example = "password")
    private String password;
    @Getter
    @Setter
    private Roles role;
}
