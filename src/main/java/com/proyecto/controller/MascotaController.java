package com.proyecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Mascot;
import com.proyecto.service.MascotaService;
import com.proyecto.util.AppSettings;
import com.proyecto.util.Mensaje;

@RestController
@RequestMapping("/url/mascota")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class MascotaController {

	@Autowired
	private MascotaService mascotaService;
	
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<Mascot>> listaMascota() {
		List<Mascot> lista = mascotaService.listaMascota();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Mascot> getById(@PathVariable("id") Integer id){
		if (!mascotaService.existsById(id))
			return new ResponseEntity(new Mensaje("No existe la mascota"), HttpStatus.NOT_FOUND);
		Mascot mascot = mascotaService.findById(id).get();
		return new ResponseEntity<Mascot>(mascot, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaMascota(@RequestBody Mascot obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Mascot objSalida = mascotaService.insertaActualizaMascota(obj);
			if (objSalida == null) {
				salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
			} else {
				salida.put("mensaje", AppSettings.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<?> updateMascota(@PathVariable("id") Integer id,@RequestBody Mascot obj, BindingResult bindingResult) {
		
		Mascot mascotCurrent = mascotaService.findById(id).get();
		Mascot mascotUpdate = null;
		
		Map<String, Object> salida = new HashMap<>();
		
		try {
			
			if (!mascotaService.existsById(id))
				return new ResponseEntity(new Mensaje("No existe el id mascota"), HttpStatus.NOT_FOUND);
			
			if(bindingResult.hasErrors()) {
				salida.put("mensaje", "Campos erróneos");
				return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.BAD_REQUEST);
			}
				
			mascotCurrent.setName(obj.getName());
			mascotCurrent.setRace(obj.getRace());
			mascotCurrent.setSex(obj.getSex());
			mascotCurrent.setUser(obj.getUser());
			mascotCurrent.setDepartment(obj.getDepartment());
			
			Mascot objSalida = mascotaService.insertaActualizaMascota(obj);
			
			if (objSalida == null) {
				salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
			} else {
				salida.put("mensaje", AppSettings.MENSAJE_ACT_EXITOSO);
			}
		} catch (DataAccessException e) {
			salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
			salida.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		salida.put("mensaje", AppSettings.MENSAJE_ACT_EXITOSO);
		salida.put("Mascota", mascotUpdate);
		return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.OK);
	}


	@PutMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteMascot(@RequestBody Mascot mascot, @PathVariable("id") Integer id) {
		
		Mascot mascotCurrent = mascotaService.findById(id).get();
		Mascot mascotDelete = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			if (mascotCurrent == null) {
				response.put("mensaje", AppSettings.MENSAJE_ELI_NO_EXISTE_ID);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			mascotCurrent.setStatus(1);
			response.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
			mascotDelete = mascotaService.insertaActualizaMascota(mascotCurrent);
			
		} catch (DataAccessException e) {
			response.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
