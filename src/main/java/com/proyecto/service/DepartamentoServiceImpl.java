package com.proyecto.service;

import com.proyecto.entity.Department;
import com.proyecto.repository.DepartamentoRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
	private DepartamentoRepository repositorio;
	
	@Override
	public Department insertaActualizaDepartament(Department obj) {
		return repositorio.save(obj);
	}

	@Override
	public List<Department> listDepartament() {
		return repositorio.findAll();
	}

	@Override
	public Optional<Department> findById(Integer id) {
		return repositorio.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return repositorio.existsById(id);
	}

	@Override
	public boolean existsByNumber(Integer number) {
		return repositorio.existsByNumber(number);
	}

	@Override
	public Department update(Department department) {
		return repositorio.save(department);
	}

	@Override
	public Optional<Department> getByNumber(Integer numberDepartment) {
		return repositorio.findByNumber(numberDepartment);
	}
	
	

}
