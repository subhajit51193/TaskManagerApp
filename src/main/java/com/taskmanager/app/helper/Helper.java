package com.taskmanager.app.helper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.taskmanager.app.entity.User;
import com.taskmanager.app.model.AuthenticationResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This class contains some repetitive logics which are used in the application 
 */
@Component
public class Helper {

	/*
	 * Creates a random string for Id
	 */
	public String createRandomStringId() {
		
		String newStringId = UUID.randomUUID().toString();
		return newStringId;
	}
	
	/*
	 * Creates a cookie and returns authentication response
	 * 
	 * @param User -> user object with necessary details
	 * @param AccessToken -> generated accessToken
	 * @param RefreshToken -> generated refreshToken
	 * @param HttpServletResponse -> An HTTP response to add authentication tokens
	 * 
	 * @return AuthenticationResponse -> An instance of authenticationResponse containing token
	 * and user information
	 */
	public AuthenticationResponse createCookie(User user, String accessToken, String refreshToken, HttpServletResponse response) {
		
		Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setMaxAge(60 * 1000 * 4);
        accessTokenCookie.setDomain("localhost");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(24 * 60 * 60 * 1000 * 7);
        refreshTokenCookie.setDomain("localhost");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

		AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
				.email(user.getEmail())
				.userId(user.getUserId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.roles(user.getRoles())
				.token(accessToken)
				.build();
		
		return authenticationResponse;
	}
}
