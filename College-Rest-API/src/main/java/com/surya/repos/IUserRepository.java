package com.surya.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surya.entitys.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
