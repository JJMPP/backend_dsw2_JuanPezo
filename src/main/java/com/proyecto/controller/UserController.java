package com.proyecto.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.proyecto.dto.LoginUser;
import com.proyecto.dto.NewUser;
import com.proyecto.entity.Role;
import com.proyecto.entity.User;
import com.proyecto.enums.RoleName;
import com.proyecto.jwt.JwtDto;
import com.proyecto.jwt.JwtTokenProvider;
import com.proyecto.service.RoleService;
import com.proyecto.service.UserService;
import com.proyecto.util.Mensaje;

import static com.proyecto.util.UserMessage.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	JwtTokenProvider jwtProvider;
	
	@GetMapping("/listrole")
	public ResponseEntity<List<Role>> obtenerListDepartamento(){
		List<Role> lista = roleService.list();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") Integer id){
		if (!userService.existsById(id))
			return new ResponseEntity(new Mensaje(NO_USER), HttpStatus.NOT_FOUND);
		User user = userService.findById(id).get();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return new ResponseEntity(INCORRECT_USERNAME_OR_PASSWORD, HttpStatus.BAD_REQUEST);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);		
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<User>> findAll() {
		List<User> lista = userService.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/register")	
	public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
		
		Map<String, Object> response = new HashMap<>();
		
		if(bindingResult.hasErrors()) {
			response.put("mensaje", WRONG_FIELDS_OR_INVALID_EMAIL);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(userService.existsByUsername(newUser.getUsername())) {
			response.put("mensaje", USERNAME_ALREADY_EXISTS);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if(userService.existsByEmail(newUser.getEmail())){
			response.put("mensaje", EMAIL_ALREADY_EXISTS);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			User user = new User(
					newUser.getName(),
					newUser.getUsername(),
					newUser.getEmail(),
					passwordEncoder.encode(newUser.getPassword()),
					newUser.getDni(),
					newUser.getPhone());
			
			Set<Role> roles = new HashSet<>();
			if (newUser.getRoles().contains("admin"))
				roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
			if (newUser.getRoles().contains("owner"))
				roles.add(roleService.getByRoleName(RoleName.ROLE_OWNER).get());
			if (newUser.getRoles().contains("counter"))
				roles.add(roleService.getByRoleName(RoleName.ROLE_COUNTER).get());
			user.setRoles(roles);
			userService.save(user);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", USER_SUCCESSFULLY_CREATED);
		response.put("Usuario", newUser);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@Valid @PathVariable("id") Integer id, @RequestBody User user, BindingResult bindingResult) {
				
		User userActual = userService.findById(id).get();
		User userUpdate = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (!userService.existsById(id)) {
			response.put("mensaje", NO_USER);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(bindingResult.hasErrors()) {
			response.put("mensaje", WRONG_FIELDS_OR_INVALID_EMAIL);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(userService.existsByUsername(user.getUsername()) && userService.getByUsername(user.getUsername()).get().getId() != id) {
			response.put("mensaje", USERNAME_ALREADY_EXISTS);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		if(userService.existsByEmail(user.getEmail()) && userService.getByEmail(user.getEmail()).get().getId() != id) {
			response.put("mensaje", EMAIL_ALREADY_EXISTS);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			userActual.setName(user.getName());
			userActual.setUsername(user.getUsername());
			userActual.setEmail(user.getEmail());
//			userActual.setPassword(passwordEncoder.encode(user.getPassword()));
			userActual.setDni(user.getDni());
			userActual.setPhone(user.getPhone());
			
			userUpdate = userService.update(userActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", USER_SUCCESSFULLY_UPDATED);
		response.put("Usuario", userUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}
	
	@PutMapping("/deleteUser/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody User user, @PathVariable("id") Integer id) {
		
		User userActual = userService.findById(id).get();
		User userDeleted = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (userActual == null) {
				return ResponseEntity.noContent().build();
			}
			userActual.setStatus(1);
			response.put("mensaje", DELETED_USER);
			userDeleted = userService.update(userActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(response);
	}

}
