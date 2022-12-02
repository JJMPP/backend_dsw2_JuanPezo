package com.proyecto.service;

import com.proyecto.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartamentoService {
	
	public Department insertaActualizaDepartament(Department obj);
	public List<Department> listDepartament();
	public Optional<Department> findById(Integer id);
	public boolean existsById(Integer id);
	public boolean existsByNumber(Integer number);
	
	public Department update(Department department);
	public Optional<Department> getByNumber(Integer numberDepartment);

}
