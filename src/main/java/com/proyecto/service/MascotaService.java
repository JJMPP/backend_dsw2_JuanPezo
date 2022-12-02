package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.entity.Mascot;

public interface MascotaService {

	public List<Mascot> listaMascota();	
	public Mascot insertaActualizaMascota(Mascot obj);
	public Optional<Mascot> findById(Integer id);
	public boolean existsById(Integer id);
}
