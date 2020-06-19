package com.easyfrutas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Producto;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;


@Controller
@RequestMapping("/carrito")
public class CarritoController {
	
	
	@Autowired
	UsuarioRepositorio usure;
	@Autowired
	CarritoRepositorio carrRe;
	
	
	@PostMapping("add/{loco}")
	public String loco(@PathVariable int loco,@RequestParam("cantidad") double cantidad) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		System.err.println("este es el email autenticado"+email);
		Usuario usu = usure.findByEmail(email);
		Carrito carr = carrRe.findById(usu).orElse(null);
		
		if (carr==null)
		{
			carr=new Carrito();
		
			Producto prod= new Producto();
			prod.setId(loco);
			carr.getLista().put(prod, cantidad);
			
			
		}
		
		
		
		
		return "redirect:/productos";
	}
	
	
	


}
