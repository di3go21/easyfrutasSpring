package com.easyfrutas.model;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity @AllArgsConstructor @NoArgsConstructor
public class Venta {
	
	@Id @GeneratedValue
	private long id;
	
	@ElementCollection
	private Map<Producto,Double> articulosPrecio;
	
	@ManyToOne
	private Usuario usuario;
	
	private double precioTotal;
	
	private String direccion;
	

}
