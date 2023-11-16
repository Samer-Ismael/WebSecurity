package com.SpringSecurity.Samer.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public enum Roles implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
