package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Visit;
import com.proyecto.entity.Visitor;
import com.proyecto.repository.VisitRepository;
import com.proyecto.repository.VisitorRepository;

@Service
public class VisitServiceImpl implements VisitService{

	@Autowired
	private VisitorRepository repository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Override
	public List<Visitor> listaVisitante() {
		return repository.findAll();
	}

	@Override
	public Visitor insertaActualizaVisita(Visitor obj) {
		return repository.save(obj);
	}

	@Override
	public Visitor createVisitor(Visitor obj) {
		return repository.save(obj);
	}

	@Override
	public Optional<Visitor> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public boolean existsByName(String name) {
		return repository.existsByName(name);
	}

	@Override
	public boolean existsByLastname(String lastname) {
		return repository.existsByLastname(lastname);
	}

	@Override
	public boolean existsByDni(String dni) {
		return repository.existsByDni(dni);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return repository.existsByPhone(phone);
	}

	@Override
	public List<Visitor> findVisitorByDni(String dni) {
		return repository.findByDniContaining(dni);
	}
	
	

	@Override
	public Optional<Visit> findByIdVisit(Integer id) {
		return visitRepository.findById(id);
	}
	

	@Override
	public Visit update(Visit obj) {
		return visitRepository.save(obj);
	}
	

	@Override
	public List<Visit> listVisit() {
		return visitRepository.findAll();
	}

	@Override
	public Visit createVisit(Visit obj) {
		return visitRepository.save(obj);
	}

}
