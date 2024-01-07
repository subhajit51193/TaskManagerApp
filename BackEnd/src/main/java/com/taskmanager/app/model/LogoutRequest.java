package com.taskmanager.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogoutRequest {

    private String userEmail;
    private String token;
    private String refreshToken;
}
