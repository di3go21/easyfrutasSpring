package com.easyfrutas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
public class Producto {
	
	@Id @GeneratedValue
	private Integer id;
	private String nombre;
	private double precio;
	private double stock;
	private String imagen;

}
