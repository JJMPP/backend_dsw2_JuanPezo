package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.proyecto.entity.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer>{
	
	boolean existsByName(String name);
	boolean existsByLastname(String lastname);
	boolean existsByDni(String dni);
	boolean existsByPhone(String phone);
	
//	@Query("select v from visitors v where v.dni like :filtro")
//	public List<Visitor> findByDni(@Param("filtro") String dni);

	List<Visitor> findByDniContaining(@Param("dni") String dni);
	
}
