package com.practice.praticando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.praticando.entities.User;

public interface UserRepository extends JpaRepository <User, Long>{
	
	User findByEmail(String email); //QueryMethods

}
