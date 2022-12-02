package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.Incidence;
import com.proyecto.entity.PaymentReceipt;

public interface IncidenceService {
	
	public Incidence createIncidence(Incidence obj);
	public List<Incidence> listIncidence();
	public Incidence findById(Integer id);
	
	public abstract List<Incidence> listIncidenceParams(Integer department, Integer status, String cause);

}
