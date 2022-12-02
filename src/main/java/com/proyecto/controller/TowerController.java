package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Tower;
import com.proyecto.service.TowerService;
import com.proyecto.util.AppSettings;

@RestController
@RequestMapping("/url/tower")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TowerController {
	
	@Autowired
	private TowerService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Tower>> obtenerListDepartamento(){
		List<Tower> lista = service.listTower();
		return ResponseEntity.ok(lista);
	}

}
