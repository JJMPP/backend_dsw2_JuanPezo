package com.proyecto.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proyecto.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtTokenProvider jwtProvider;
	
	@Autowired
	UserServiceImpl userDetailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filter)
			throws ServletException, IOException {
		try {
			String token = getToken(req);
			
			 if (token != null && jwtProvider.validateToken(token)) {
				 String userName = jwtProvider.getUsernameFromToken(token);
				 UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
				 UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				 SecurityContextHolder.getContext().setAuthentication(auth);
			 }
		} catch (Exception e) {
			log.error("Falló en el método doFilter " + e.getMessage());
			e.printStackTrace();
		}
		filter.doFilter(req, res);		
	}
	
	private String getToken(HttpServletRequest req) {
		String header = req.getHeader("Authorization");		
		if (header != null && header.startsWith("Bearer"))
			return header.replace("Bearer ", "");
		return null;
		
	}

}
