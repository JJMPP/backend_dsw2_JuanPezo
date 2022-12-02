package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.entity.DepartmentType;

@Repository
public interface DepartmentTypesRepository extends JpaRepository<DepartmentType ,Integer> {

}
