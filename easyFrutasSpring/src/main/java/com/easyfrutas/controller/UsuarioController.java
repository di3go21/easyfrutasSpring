package com.easyfrutas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.easyfrutas.model.Producto;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	@Autowired
	public ProductoRepositorio productoRepositorio;

	@GetMapping({"","/hola"})
	public String bienvenida(Model modelo) {
		
		List<Usuario> todos= usuarioRepositorio.findAll();
		todos.forEach(x->System.err.println(x));
		modelo.addAttribute("usuarios",todos);
		
		return "welcome";
	}
	
	@GetMapping("/productos")
	public String productos(Model modelo) {
		System.out.println("loco?");
		List<Producto> todosProductos= productoRepositorio.findAll();
		todosProductos.forEach(x->System.err.println(x));
		modelo.addAttribute("productos",todosProductos);
		
		return "productos";
	}


}
