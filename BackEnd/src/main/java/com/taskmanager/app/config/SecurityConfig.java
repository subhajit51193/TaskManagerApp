package com.taskmanager.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.taskmanager.app.filter.UserElegibilityFilter;

/**
 * This class contains security configuration of the application
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserElegibilityFilter userElegibilityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf((csrf) ->
					csrf.disable())
//			.cors((cors) ->
//					cors.disable())
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/api/register").permitAll()
					.requestMatchers("/api/authenticate").permitAll()
					.requestMatchers("/api/admin/allTasks").hasRole("ADMIN")
					.anyRequest().authenticated()
			)
			.httpBasic((basic) -> Customizer.withDefaults());
		
		http
			.oauth2ResourceServer((oauth2) -> oauth2
					.jwt(jwt -> Customizer.withDefaults()))
			.sessionManagement((session) -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.httpBasic((basic) -> Customizer.withDefaults());

		http
			.exceptionHandling((ex) ->{
				ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
				ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
			});
		
		http.addFilterAfter(userElegibilityFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
