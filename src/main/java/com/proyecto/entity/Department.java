package com.proyecto.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	@Column(unique = true)
	private int number;
	private int floor;
	@Column(name = "number_rooms")
	private int numberRooms;
	private int status;
	@Column(name = "square_meters")
	private int squareMeters;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")	
	@Column(name = "join_date")
	private Date joinDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tower_id")
	private Tower tower;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_types_id")
	private DepartmentType departmentTypes;	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@PrePersist
	public void prePersist() {
		this.status = 0;
		this.joinDate = new Date();
	}	
	
}
