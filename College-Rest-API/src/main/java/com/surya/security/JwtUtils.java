package com.surya.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.surya.ApplicationProperties;
import com.surya.exceptions.InvalidTokenException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtUtils {

	private final ApplicationProperties properties;

	// Generate a JWT token for the given email
	public String generateToken(String email) {
		Algorithm algorithm = Algorithm.HMAC256(properties.jwt().secretKey());
		return JWT.create().withIssuer(properties.jwt().issuer()).withSubject(email)
				.withExpiresAt(generateExpirationDate(properties.jwt().expireInseconds())).sign(algorithm);
	}

	// Generate expiration date based on the provided expiration time in seconds
	private Date generateExpirationDate(Long expireInSeconds) {
		return new Date(System.currentTimeMillis() + (expireInSeconds * 1000));
	}

	

	// Extract the username from the token (subject)
	public String validateToken(String accessToken) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(properties.jwt().secretKey());
			return JWT.require(algorithm).withIssuer(properties.jwt().issuer()).build().verify(accessToken)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new InvalidTokenException("Invalid token", e);
		}
	}
}
