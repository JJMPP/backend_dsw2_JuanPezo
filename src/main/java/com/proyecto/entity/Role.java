package com.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.proyecto.enums.RoleName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private RoleName roleName;
	
	public Role() {
		super();
	}

	public Role(RoleName roleName) {
		super();
		this.roleName = roleName;
	}
}
