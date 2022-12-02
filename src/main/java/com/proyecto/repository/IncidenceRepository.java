package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.entity.Incidence;

public interface IncidenceRepository extends JpaRepository<Incidence, Integer> {
	

	@Query("select v from Incidence v where (:p_department is 0 or v.department.id = :p_department)"
										  + "and (:p_status is 0 or v.status like :p_status)"
										  + "and (:p_cause is '' or v.cause like :p_cause)")
	public List<Incidence> listIncidenceByParameter(@Param("p_department") Integer department, 
												    @Param("p_status") Integer status,
												    @Param("p_cause") String cause);

}
