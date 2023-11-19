package com.SpringSecurity.Samer.model;

import lombok.Data;

@Data
public class ChangingPassword {

    String oldPassword;
    String newPassword;
    String confirmPassword;
}
