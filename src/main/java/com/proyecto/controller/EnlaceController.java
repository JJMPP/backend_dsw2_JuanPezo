package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class EnlaceController {

	//Registro
	@RequestMapping("/")
	public String verLogin() {	return "sistemaLogin";  }
	
	@RequestMapping("/verIntranetHome")
	public String verIntranetHome() {	return "sistemaInicio";  }

}
