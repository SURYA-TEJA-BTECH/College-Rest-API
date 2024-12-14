package com.surya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.surya.exceptions.InvalidCredentialsException;
import com.surya.repos.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

	@Autowired
	private final IUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email)  {
		return repository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid username"));
	}

	

}
