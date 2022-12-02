package com.proyecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.proyecto.entity.Department;
import com.proyecto.entity.DepartmentType;
import com.proyecto.service.DepartamentoService;
import com.proyecto.service.DepartmentTypesService;
import com.proyecto.util.AppSettings;
import com.proyecto.util.Mensaje;

@RestController
@RequestMapping("/url/departamento")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class DepartamentoController {
		
	@Autowired
	private DepartamentoService  departamentoService;
	
	@Autowired
	private DepartmentTypesService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Department>> obtenerListDepartamento(){
		List<Department> lista = departamentoService.listDepartament();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/listdepartmenttypes")
	public ResponseEntity<List<DepartmentType>> getListDepartamentTypes(){
		List<DepartmentType> lista = service.listDepartamentTypes();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Department> getById(@PathVariable("id") Integer id){
		if (!departamentoService.existsById(id))
			return new ResponseEntity(new Mensaje("No existe departamento"), HttpStatus.NOT_FOUND);
		Department department = departamentoService.findById(id).get();
		return new ResponseEntity<Department>(department, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaDepartamento(@RequestBody Department obj){
		Map<String, Object> salida = new HashMap<>();		
		try {
			if (departamentoService.existsByNumber(obj.getNumber()))
				return new ResponseEntity(new Mensaje("Este número departamento ya existe"), HttpStatus.NOT_FOUND);
			Department objSalida = departamentoService.insertaActualizaDepartament(obj);
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
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateDepartament(@Valid @PathVariable("id") Integer id, @RequestBody Department obj, BindingResult bindingResult){
		
		Department departmentCurrent = departamentoService.findById(id).get();
		Department departmentUpdate = null;
		
		Map<String, Object> salida = new HashMap<>();
		
		try {
			if (!departamentoService.existsById(id))
				return new ResponseEntity(new Mensaje("No existe el id departamento"), HttpStatus.NOT_FOUND);
			
			if(bindingResult.hasErrors()) {
				salida.put("mensaje", "Campos erróneos");
				return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.BAD_REQUEST);
			}
			
			if (departamentoService.existsByNumber(obj.getNumber()) && departamentoService.getByNumber(obj.getNumber()).get().getId() != id) {
				salida.put("mensaje", "Número de Departamento ya existe");
				return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.NOT_FOUND);
			}
			
			departmentCurrent.setName(obj.getName());
			departmentCurrent.setNumber(obj.getNumber());
			departmentCurrent.setFloor(obj.getFloor());
			departmentCurrent.setNumberRooms(obj.getNumberRooms());
			departmentCurrent.setSquareMeters(obj.getSquareMeters());
			departmentCurrent.setTower(obj.getTower());
			departmentCurrent.setDepartmentTypes(obj.getDepartmentTypes());
			departmentCurrent.setUser(obj.getUser());
			
			departmentUpdate = departamentoService.update(departmentCurrent);
			
		} catch (DataAccessException e) {
			salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
			salida.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		salida.put("mensaje", AppSettings.MENSAJE_ACT_EXITOSO);
		salida.put("Departamento", departmentUpdate);
		return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.OK);
	}
	
	@PutMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteDepartment(@RequestBody Department department, @PathVariable("id") Integer id) {
		
		Department departmentCurrent = departamentoService.findById(id).get();
		Department departmentDeleted = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (departmentCurrent == null) {
				response.put("mensaje", AppSettings.MENSAJE_ELI_NO_EXISTE_ID);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			departmentCurrent.setStatus(1);
			response.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
			departmentDeleted = departamentoService.update(departmentCurrent);
			
		} catch (DataAccessException e) {
			response.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(response);
	}
	

}
