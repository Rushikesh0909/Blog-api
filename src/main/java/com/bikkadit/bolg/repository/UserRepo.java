package com.bikkadit.bolg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.bolg.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
