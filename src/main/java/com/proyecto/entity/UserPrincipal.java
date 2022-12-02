package com.proyecto.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	private int id;	
	private String name;
	private String username;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;	

	public UserPrincipal(int id, String name, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.id = id;
	}

	public static UserPrincipal build(User user) {
		List<GrantedAuthority> authorities =
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role
						.getRoleName().name())).collect(Collectors.toList());
		
		return new UserPrincipal(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
	}
	
	public int getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
