package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.entity.Mascot;
import com.proyecto.repository.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {

	@Autowired
	private MascotaRepository Repository;
	
	@Override
	public List<Mascot> listaMascota() {
		return Repository.findAll();
	}

	@Override
	public Mascot insertaActualizaMascota(Mascot obj) {
		return Repository.save(obj);
	}

	@Override
	public Optional<Mascot> findById(Integer id) {
		return Repository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return Repository.existsById(id);
	}

}
