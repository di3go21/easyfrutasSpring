package com.easyfrutas.model;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity @AllArgsConstructor
public class Venta {
	
	@Id @GeneratedValue
	private long id;
	
	@ElementCollection
	private Map<Producto,Double> articulosPrecio;
	
	@ManyToOne
	private Usuario usuario;
	
	

}
