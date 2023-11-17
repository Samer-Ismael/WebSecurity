package com.SpringSecurity.Samer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class UserToShowToSwagger {

    @ApiModelProperty(value = "username", example = "username")
    private String username;
    @ApiModelProperty(value = "password", example = "password")
    private String password;

}
