package com.easyfrutas.controller.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.servicios.CarritoServicio;

@ControllerAdvice
public class ProductoErroresController {
	final String MENSAJE="El stock ha variado y no podemos satisfacer tu petición, por favor revisa el carrito";
	
	@Autowired
	private CarritoServicio carritoServicio;

	@ExceptionHandler(value = ProductoSinStockException.class)
	public String sinstock(ProductoSinStockException exception, Model model) {
		
		Carrito carr=carritoServicio.arreglaCarrito(exception.getCarr());
		model.addAttribute("mensaje",this.MENSAJE);
		
		model.addAttribute("carrito", carr);
		
		return "carrito";
	}
	
	
}
