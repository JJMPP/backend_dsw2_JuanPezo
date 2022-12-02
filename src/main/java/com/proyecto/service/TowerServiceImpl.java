package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Tower;
import com.proyecto.repository.TowerRepository;

@Service
public class TowerServiceImpl implements TowerService {
	
	@Autowired
	private TowerRepository repository;

	@Override
	public List<Tower> listTower() {
		return repository.findAll();
	}

}
