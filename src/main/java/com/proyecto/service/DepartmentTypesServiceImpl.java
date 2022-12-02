package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.DepartmentType;
import com.proyecto.repository.DepartmentTypesRepository;

@Service
public class DepartmentTypesServiceImpl implements DepartmentTypesService {
	
	@Autowired
	private DepartmentTypesRepository repository;

	@Override
	public List<DepartmentType> listDepartamentTypes() {
		return repository.findAll();
	}

}
