package com.taskmanager.app.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.app.entity.Role;
import com.taskmanager.app.entity.User;
import com.taskmanager.app.exception.UserDetailsNotValidException;
import com.taskmanager.app.helper.Helper;
import com.taskmanager.app.model.AuthenticationResponse;
import com.taskmanager.app.model.LogoutRequest;
import com.taskmanager.app.model.MessageResponse;
import com.taskmanager.app.repository.RoleRepository;
import com.taskmanager.app.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This class contains all the methods implementations related to authentication services
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*
	 * Register new user
	 * 
	 * @param: User and Role -> User is registered with associated roles
	 * @param HttpServletResponse -> An HTTP response to add authentication tokens
	 * 
	 * @return: AuthenticationResponse -> An instance of authenticationResponse containing token
	 * and user information
	 * 
	 * @throws: UserNotValidException -> throws this exception in case invalid details provided by client
	 * 
	 * @throws: UserAlreadyExistsWithSameEmailException -> throws this exception if user with same email already 
	 * exists in database									
	 * 
	 */
	public AuthenticationResponse registerUser(User user,HttpServletResponse response) throws UserDetailsNotValidException {
		
		if (user == null) {
			
			throw new UserDetailsNotValidException("The User details are not valid !! Please recheck and try again!!!");
		}
		
		Set<Role> roles = user.getRoles();
		
		//In case user is not giving any roles then new default role will be created and added
		if(roles == null || roles.isEmpty()) {
			
			Optional<Role> opt = roleRepository.findByRoleName("ROLE_USER");
			
			//If default role is not saved in DB then new will be created
			if (opt.isPresent()) {
				
				Role role = opt.get();
				roles.add(role);
			}
			
			//If default role is saved then existing role will be added with the user
			else {
				
				Role role = new Role();
		        role.setRoleId(helper.createRandomStringId());
		        role.setRoleName("ROLE_USER");
		        roles.add(role);
			}   
	    }
		
		//setting common values and save the data
		
		for (Role role : roles) {
			
			Optional<Role> opt = roleRepository.findByRoleName(role.getRoleName());
			
			//if role is already present then updating it to avoid duplicate object
			if (opt.isPresent()) {
				
				Role existingRole = opt.get();
				role.setRoleId(existingRole.getRoleId());
			}
			else {
				
				role.setRoleId(helper.createRandomStringId());
			}
		}
		
		//finally setting all data with user and save
		user.setRoles(roles);
		user.setUserId(helper.createRandomStringId());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User registeredUser = userRepository.save(user);
		
		//Creating accessToken and RefreshToken
		String accessToken = jwtTokenService.generateToken(user.getEmail());
		String refreshToken = jwtTokenService.generateRefreshToken(user.getEmail());

		//Creating cookie with all the variables and return a response
		AuthenticationResponse authenticationResponse = helper.createCookie(registeredUser, accessToken, refreshToken, response);
		
		return authenticationResponse;
	}
	
	/*
	 * Authenticates user using password and email during login
	 * 
	 * @param: Authentication -> basic authentication is used by spring on user given email and password
	 * @param HttpServletResponse -> An HTTP response to add authentication tokens
	 * 
	 * @return: AuthenticationResponse -> An instance of authenticationResponse containing token
	 * and user information							
	 */
	public AuthenticationResponse authenticateUser(Authentication authentication,HttpServletResponse response) {
		
		//Getting email after basic authentication is done
		String email = authentication.getName();
		Optional<User> opt  = userRepository.findByEmail(email);
		User user = opt.get();
		
		//Creating accessToken and RefreshToken
		String accessToken  = jwtTokenService.generateToken(email);
		String refreshToken = jwtTokenService.generateRefreshToken(user.getEmail());
		
		//Creating cookie with all the variables and return a response
		AuthenticationResponse authenticationResponse = helper.createCookie(user, accessToken, refreshToken, response);
		
		return authenticationResponse;
	}

    /**
     * Logs out a user by deleting and disabling accessToken and refreshToken
     *
     * @param logoutRequest The logout request containing the user's email and accessToken and refreshToken
     * @return A MessageResponse indicating the success of the logout operation
     */
	@Override
	public MessageResponse logoutUser(LogoutRequest logoutRequest, HttpServletRequest request,
			HttpServletResponse response) {
		
	     /*
	     * Fetching cookies from HttpRequest and modify its age. This will make sure 
	     * that cookie is deleted and not used further to access any endPoints
	     */
		 Cookie[] cookies = request.getCookies();
		 if (cookies != null) {
			 for(Cookie cookie : cookies) {
				 if (cookie.getName().equals("accessToken") || cookie.getName().equals("refreshToken")) {
					 cookie.setValue("");
					 cookie.setPath("/");
					 cookie.setMaxAge(0); // Setting the cookie age to 0 will delete it
					 response.addCookie(cookie);
				 }
			 }
		 }
		 
		 MessageResponse messageResponse = MessageResponse.builder()
				 .message("You are now successfully logged out! ")
				 .isSuccess(true)
				 .build();
		 
		 return messageResponse;
	}
}
