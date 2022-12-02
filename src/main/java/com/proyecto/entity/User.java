package com.proyecto.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	@Column(unique = true)
	private String username;
	
	@NotNull
	@Email
	@Column(unique = true)
	private String email;	
	
	@JsonIgnore
	@NotNull
	private String password;
	
	@Column(unique = true)
	private String dni;
	
	@Column(unique = true)
	private String phone;
	
//	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")	
	@Column(name = "join_date")
	private Date joinDate;
	
	private int status;
	
	public User() {
		super();
	}
	
	public User(@NotNull String name, @NotNull String username, @NotNull String email, @NotNull String password,
			String dni, String phone) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dni = dni;
		this.phone = phone;
	}

	@PrePersist
	public void prePersist() {
		this.joinDate = new Date();
		this.status = 0;
	}

	
}
