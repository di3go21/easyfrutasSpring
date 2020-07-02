package com.easyfrutas.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"email"}))

@EntityListeners(AuditingEntityListener.class)
public class Usuario implements Serializable{
	
	@Id @GeneratedValue
	private long id;
	
	@NotNull
	@Size(min=2, max=30)
	private String nombre;
	@NotNull
	@Size(min=2, max=30)
	private String apellido;
	@NotNull
	@Email
	private String email;
	@NotNull
	private String contrasenia;
	@NotNull
	@Size(min=15, max=50)
	private String direccion;
	@NotNull
	@Size(min=9, max=15)
	private String telefono;
	private boolean verificado;
	private String codigoValidacion;

	private LocalDateTime fechaReg=LocalDateTime.now();
	
	
	
	
	
	
}