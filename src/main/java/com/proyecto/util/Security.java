package com.proyecto.util;

public class Security {
	
	public static final long EXPIRATION_TIME = 432_000_000;//5 dias expresado en milesegundos
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token no puede ser verificado";
	public static final String GRUPO_02 = "Grupo 02";
	public static final String AUTHORITIES = "authorities";
	public static final String FORBIDDEN_MESSAGE = "Debes iniciar sesión para acceder a esta página";
	public static final String ACCESS_DENIED_MESSAGE = "Usted no tiene permiso para acceder a esta página";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String[] URLS = { "/auth/**", "/url/**" };

}
