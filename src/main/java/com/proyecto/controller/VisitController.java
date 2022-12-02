package com.proyecto.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.User;
import com.proyecto.entity.Visit;
import com.proyecto.entity.Visitor;
import com.proyecto.service.UserService;
import com.proyecto.service.VisitService;
import com.proyecto.util.AppSettings;

@RestController
@RequestMapping("/url/visita")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class VisitController {

	@Autowired
	private VisitService visitService;
		
	@Autowired
	private UserService userService;
	
	@GetMapping("/findUserListByRole")
	@ResponseBody
	public List<User> findUserListByRole(@RequestParam int id) {
	  return userService.findUserListByRoleId(id);
	}
	
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<Visitor>> listVisitor() {
		List<Visitor> lista = visitService.listaVisitante();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/listaVisit")
	@ResponseBody
	public ResponseEntity<List<Visit>> listVisit() {
		List<Visit> listaVisit = visitService.listVisit();
		return ResponseEntity.ok(listaVisit);
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaVisitante(@Valid @RequestBody Visitor obj) {
		Map<String, Object> response = new HashMap<>();
		
		if(visitService.existsByName(obj.getName())) {
			response.put("mensaje", "El nombre ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if(visitService.existsByLastname(obj.getLastname())) {
			response.put("mensaje", "El apellido ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if(visitService.existsByPhone(obj.getPhone())) {
			response.put("mensaje", "El teléfono ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if(visitService.existsByDni(obj.getDni())){
			response.put("mensaje", "El Dni del visitante ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			Visitor objSalida = visitService.createVisitor(obj);
			if (objSalida == null) {
				response.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
			} else {
				response.put("mensaje", AppSettings.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/createVisit")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> createVisit(@Valid @RequestBody Visit obj) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Visit objSalida = visitService.createVisit(obj);
			
			if (objSalida == null) {
				response.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
			} else {
				response.put("mensaje", AppSettings.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/changeStatus/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateStatusComment(@RequestBody Visit visit, @PathVariable("id") Integer id) {
		
		Visit visitCurrent = visitService.findByIdVisit(id).get();
		Visit changeStatus = null;
		Map<String, Object> response = new HashMap<>();
		
		if (visitCurrent == null) {
			response.put("mensaje", "No se pudo actualizar, el ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			if (visitCurrent.getStatus() != 1) {
				visitCurrent.setStatus(1);
				visitCurrent.setExitDate(new Date());
				response.put("mensaje", "Estado y fecha de salida actualizado");
				changeStatus = visitService.update(visitCurrent);				
			}
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el estado y fecha de salida en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/findVisitorByDni/{dni}")
	@ResponseBody
	public ResponseEntity<List<Visitor>> findVisitorByDni(@PathVariable String dni) {
		
		List<Visitor> list = null;
		try {
			list = visitService.findVisitorByDni(dni);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ResponseEntity.ok(list);
		
	}

	
	
}
