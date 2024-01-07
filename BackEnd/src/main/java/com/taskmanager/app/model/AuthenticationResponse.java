package com.taskmanager.app.model;

import java.util.Set;

import com.taskmanager.app.entity.Role;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class AuthenticationResponse {

    private String token;
    private String email;
    private String userId;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
