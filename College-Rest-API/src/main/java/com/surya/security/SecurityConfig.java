package com.surya.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

	private JwtAuthenticationFilterr authenticationFilterr;

	private static final String[] PUBLIC_RESOURCES = { "/actuator/**", "/error", "/swagger-ui/**", "/v3/api-docs/**" };

	@Bean
	PasswordEncoder createEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		String[] unsecuredPaths = { "/register", "/auth/api/login", "/auth/api/register" };

		return httpSecurity.csrf(csrf -> csrf.disable()) // Disable CSRF
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(PUBLIC_RESOURCES).permitAll() // Allow public resources
						.requestMatchers(unsecuredPaths).permitAll() // Allow login and register
						.requestMatchers(HttpMethod.POST, "/api/students/").hasAnyRole("ADMIN", "STUDENT") // Allow both ADMIN and STUDENT to register
						.requestMatchers("/api/students/**").hasRole("ADMIN") // Admin can access all /api/students endpoints (GET, PUT, DELETE)
						.anyRequest().authenticated() // Require authentication for all other requests
				).addFilterBefore(authenticationFilterr, UsernamePasswordAuthenticationFilter.class)

				.build();
	}

	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults(""); // Removes the "ROLE_" prefix
	}

}