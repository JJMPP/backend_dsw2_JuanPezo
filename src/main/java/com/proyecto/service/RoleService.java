package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Role;
import com.proyecto.enums.RoleName;
import com.proyecto.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository rolRepository;
	
	public List<Role> list() { 
		return rolRepository.findAll();
	}
	
	public Optional<Role> getByRoleName(RoleName roleName) {
		return rolRepository.findByRoleName(roleName);
	}
	
	public void save(Role role) {
		rolRepository.save(role);
	}
	
}
