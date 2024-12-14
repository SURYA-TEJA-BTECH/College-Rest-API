package com.surya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surya.dtos.LoginRequest;
import com.surya.dtos.RegistrationRequest;
import com.surya.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth/api")
@AllArgsConstructor
public class AuthenticationController {

	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		String response = userService.register(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {

		String token = userService.validateLoginCredentials(request);

		return ResponseEntity.ok(token);
	}

}
