package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Incidence;
import com.proyecto.repository.IncidenceRepository;

@Service
public class IncidenceServiceImpl implements IncidenceService{
	
	@Autowired
	private IncidenceRepository incidenceRepository;

	@Override
	public Incidence createIncidence(Incidence obj) {
		return incidenceRepository.save(obj);
	}

	@Override
	public List<Incidence> listIncidence() {
		return incidenceRepository.findAll();
	}

	@Override
	public Incidence findById(Integer id) {
		return incidenceRepository.findById(id).orElseThrow();
	}

	@Override
	public List<Incidence> listIncidenceParams(Integer department, Integer status, String cause) {
		return incidenceRepository.listIncidenceByParameter(department, status, cause);
	}

}
