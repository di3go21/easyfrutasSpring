package com.easyfrutas.controller.errors;

import com.easyfrutas.model.Carrito;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductoSinStockException extends RuntimeException {
	private Carrito carr;
	
	public ProductoSinStockException(Carrito carr) {
		this.carr=carr;
	}

	private static final long serialVersionUID = -4054310651246908335L;
	

}
