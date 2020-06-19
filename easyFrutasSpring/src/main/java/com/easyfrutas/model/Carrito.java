package com.easyfrutas.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrito implements Serializable {

	private static final long serialVersionUID = -4977023912401899056L;
	
	@Id
	private long id;
	
	@OneToOne
	private Usuario usuario;

	@ElementCollection
	Map<Producto, Double> lista;

}
