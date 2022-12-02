package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.entity.Visit;
import com.proyecto.entity.Visitor;

public interface VisitService {

	public abstract List<Visitor> listaVisitante();
	
	public abstract Visitor insertaActualizaVisita(Visitor obj);
	
	public Visitor createVisitor(Visitor obj);
	
	public Optional<Visitor> findById(Integer id);	
	
	public boolean existsByName(String name);
	
	public boolean existsByLastname(String lastname);
	
	public boolean existsByDni(String dni);
	
	public boolean existsByPhone(String phone);
	
	public List<Visitor> findVisitorByDni(String dni);
	
	
	
	public Visit createVisit(Visit obj);
	
	public Optional<Visit> findByIdVisit(Integer id);
	
	public Visit update(Visit obj);	
	
	public List<Visit> listVisit();
}
