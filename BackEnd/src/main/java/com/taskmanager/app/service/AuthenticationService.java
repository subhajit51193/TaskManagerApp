package com.taskmanager.app.service;

import org.springframework.security.core.Authentication;

import com.taskmanager.app.entity.User;
import com.taskmanager.app.exception.UserDetailsNotValidException;
import com.taskmanager.app.model.AuthenticationResponse;
import com.taskmanager.app.model.LogoutRequest;
import com.taskmanager.app.model.MessageResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

	public AuthenticationResponse registerUser(User user,HttpServletResponse response) throws UserDetailsNotValidException;
	
	public AuthenticationResponse authenticateUser(Authentication authentication,HttpServletResponse response);
	
	public MessageResponse logoutUser(LogoutRequest logoutRequest,HttpServletRequest request, HttpServletResponse response);
}
