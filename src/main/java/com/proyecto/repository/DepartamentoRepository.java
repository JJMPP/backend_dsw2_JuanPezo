package com.proyecto.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.entity.Department;

@Repository
public interface DepartamentoRepository extends JpaRepository<Department ,Integer> {
	
	boolean existsByNumber(Integer number);
	Optional<Department> findByNumber(Integer numberDepartment);

}
