package com.proyecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Incidence;
import com.proyecto.entity.User;
import com.proyecto.entity.UserPrincipal;
import com.proyecto.service.IncidenceService;
import com.proyecto.util.AppSettings;

@RestController
@RequestMapping("/url/incidence")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class IncidenceController {
	
	@Autowired
	private IncidenceService incidenceService;
	
	
	@GetMapping("/list")
	public ResponseEntity<List<Incidence>> getListIncidence(){
		List<Incidence> list = incidenceService.listIncidence();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/incidenceByParameters")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> listIncidenceByParameter(
			@RequestParam(name="depa",defaultValue = "0",required = false) Integer department,
			@RequestParam(name="status",defaultValue = "0",required = false) Integer status,
			@RequestParam(name="cause",defaultValue = "",required = false) String cause){
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			List<Incidence> lstIncidence = incidenceService.listIncidenceParams(department, status, cause);
			if(CollectionUtils.isEmpty(lstIncidence)) {
				response.put("mensaje", "No existe datos para la consulta");
			}else {
				response.put("lista", lstIncidence);
				response.put("mensaje", "Existe = "+lstIncidence.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Ocurrio un error, No existe datos para mostrar");
		}
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> createIncidence(@RequestBody Incidence obj, Authentication authentication){
		
		Map<String, Object> salida = new HashMap<>();
		
		try {
			var userPrincipal = (UserPrincipal) authentication.getPrincipal();
			User user = new User();
			user.setId(userPrincipal.getId());
			obj.setUser(user);
			
			Incidence objSalida = incidenceService.createIncidence(obj);
			
			if (objSalida == null) {
				salida.put("mensaje", "No se registró, consulte con el administrador.");
			}else {
				salida.put("mensaje", "Se registró correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "No se registró, consulte con el administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	
	@PatchMapping("/updateStateIncidence")
	@ResponseBody
	public ResponseEntity<Incidence> updateIncidence(@RequestBody Incidence obj){
		
		
		Incidence salida = new Incidence();
//		salida = incidenceService.findById(obj.getId());
		salida.setStatus(obj.getStatus());
		
		Incidence objSalida = incidenceService.createIncidence(salida);		
				
		if(objSalida==null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(objSalida);
	}

}
