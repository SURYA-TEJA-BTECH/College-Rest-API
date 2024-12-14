package com.surya.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.surya.exceptions.InvalidTokenException;
import com.surya.service.AuthorizationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilterr extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final AuthorizationService authorizationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extract the access token from the Authorization header
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			// No token found, proceed with the next filter
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = authorizationHeader.substring(7); // Remove "Bearer " prefix

		String userName = null;
		try {
			// Check if the token is expired
			userName = jwtUtils.validateToken(accessToken);

		} catch (Exception e) {
			// Any other token parsing errors
			respondWithError(response, HttpStatus.UNAUTHORIZED, "Invalid token");

			return;
		}

		// Load user details from the service
		UserDetails userDetails = authorizationService.loadUserByUsername(userName);

		if (userDetails == null) {
			respondWithError(response, HttpStatus.UNAUTHORIZED, "User not found");
			return;
		}

		// Create authentication object and set it in the security context
		Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		// Proceed with the filter chain
		filterChain.doFilter(request, response);
	}

	private void respondWithError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
		response.setContentType("application/json");
		response.setStatus(status.value());
		response.getWriter().write(String.format("{\"error\":\"%s\"}", message));
	}
}
