package com.easyfrutas.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@GeneratedValue
	private long id;

	@OneToOne
	private Usuario usuario;

	@ElementCollection
	Map<Producto, Double> lista;

	private double total;
	
	
	
	@Override
	public String toString() {

		StringBuilder loco = new StringBuilder();

		this.lista.forEach((a, b) -> {
			loco.append(a).append(" ").append(b).append('\n');

		});

		return loco.toString();

	}



	public void actualizaTotal() {
		double total=0;
		for (Entry<Producto, Double> entry : lista.entrySet()) {
		 total+=(double) Math.round((entry.getKey().getPrecio()*entry.getValue()) * 100) / 100;
		}
		this.total=(double) Math.round(total * 100) / 100;
	}

	
}
