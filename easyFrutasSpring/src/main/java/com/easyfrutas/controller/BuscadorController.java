package com.easyfrutas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyfrutas.model.Producto;
import com.easyfrutas.repositorios.ProductoRepositorio;

@Controller
public class BuscadorController {
	
	@Autowired
	public ProductoRepositorio productoRepositorio;
	
	@GetMapping("/buscar")
	public String result(  @RequestParam(name="query") String query,Model model) {
		
		List<Producto> resultado= productoRepositorio.findByNombreContainsIgnoreCase(query);
		model.addAttribute("productos",resultado);
		
		return "productos";
	}

}
