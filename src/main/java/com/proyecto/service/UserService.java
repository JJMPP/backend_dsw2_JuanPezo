package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.User;
import com.proyecto.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User update(User user) {
		return userRepository.save(user);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	public boolean existsById(Integer id) {
		return userRepository.existsById(id);
	}
	
	
	public List<User> findUserListByRoleId(int id) {
		List<User> lstUser = userRepository.findUserListByRoleId(id);
		return lstUser;
	}
	
	
	
	
}
