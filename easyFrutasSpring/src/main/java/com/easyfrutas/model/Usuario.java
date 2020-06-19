package com.easyfrutas.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"email"}))

@EntityListeners(AuditingEntityListener.class)
public class Usuario{
	
	@Id @GeneratedValue
	private long id;
	@NotNull
	private String nombre;
	@NotNull
	private String apellido;
	@NotNull
	private String email;
	@NotNull
	private String contrasenia;
	@NotNull
	private String direccion;
	@NotNull
	private String telefono;
	private boolean active;

	private LocalDateTime fechaReg=LocalDateTime.now();
	
	
	
	
	
	
}