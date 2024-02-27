package com.practice.praticando.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.praticando.dtos.UserDTO;
import com.practice.praticando.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping(value="/me")
	@PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
	public ResponseEntity<UserDTO> getMe() {
		UserDTO dto = service.getme();
		return ResponseEntity.ok(dto);
	}
	
	
	
	
}
