package com.surya.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surya.dtos.LoginRequest;
import com.surya.dtos.RegistrationRequest;
import com.surya.entitys.Role;
import com.surya.entitys.User;
import com.surya.exceptions.InvalidCredentialsException;
import com.surya.exceptions.UserAlreadyExistsException;
import com.surya.repos.IUserRepository;
import com.surya.security.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public String register(RegistrationRequest request) {

		findUserByEmail(request.email());
		User user = new User();
		user.setEmail(request.email());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRole(Role.valueOf(request.role().toUpperCase()));

		userRepository.save(user);

		return "succesfully registred pl login";
	}

	public String validateLoginCredentials(LoginRequest request) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				request.email(), request.password());

		Authentication authenticate = authenticationManager.authenticate(authenticationToken);

		User user = (User) authenticate.getPrincipal();

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid password");
		}

		return jwtUtils.generateToken(user.getEmail());
	}

	private void findUserByEmail(String email) {

		Optional<User> user = userRepository.findByEmail(email);

		if (user.isPresent()) {
			throw new UserAlreadyExistsException("Email is already registered");
		}

	}

}
