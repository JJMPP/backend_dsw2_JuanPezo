package com.proyecto.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUser {
	
	@NotBlank
	private String name;
	@Email
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;	
	
	private String dni;
	private String phone;

	private Set<String> roles = new HashSet<>();
	
}
