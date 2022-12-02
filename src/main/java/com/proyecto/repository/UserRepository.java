package com.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
//	public List<User> findUserByRole(int id);
	
	@Query(value = "select * from users u join users_roles ur on ur.user_id = u.id where ur.role_id = ?", nativeQuery = true)
	public abstract List<User> findUserListByRoleId(int id); 
	
//	@Query(value = "select * from users_roles ur where ur.role_id = ?", nativeQuery = true)
//	public abstract List<User> findUserListByRoleId(int id); 
	
}
