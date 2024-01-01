package com.taskmanager.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class contains security configuration of the application
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf((csrf) ->
					csrf.disable())
			.cors((cors) ->
					cors.disable())
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/api/register").permitAll()
					.requestMatchers("/api/authenticate").permitAll()
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
		
		return http.build();
	}
}