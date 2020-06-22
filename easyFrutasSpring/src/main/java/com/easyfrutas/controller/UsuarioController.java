package com.easyfrutas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.easyfrutas.model.Producto;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;
import com.easyfrutas.repositorios.VentaRepositorio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	@Autowired
	public ProductoRepositorio productoRepositorio;

	@Autowired
	public VentaRepositorio ventaRepo;

	@GetMapping("/hola")
	public String bienvenida(Model modelo) {
		
		List<Usuario> todos= usuarioRepositorio.findAll();
		todos.forEach(x->System.err.println(x));
		modelo.addAttribute("usuarios",todos);
		System.err.println("loco");
		return "welcome";
	}
	
	@GetMapping({"","/productos"})
	public String productos(Model modelo) {
		System.out.println("loco?");
		List<Producto> todosProductos= productoRepositorio.findAll();
		todosProductos.forEach(x->System.err.println(x));
		modelo.addAttribute("productos",todosProductos);
		
		return "productos";
	}

	@GetMapping("/personal")
	public String areaPersonal(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usuarioRepositorio.findByEmail(email);
		List<Venta> listaVentas= ventaRepo.findByUsuario(usu);
		
		model.addAttribute("ventas",listaVentas);
		model.addAttribute("usuario",usu);
		
		
		return "personal";
	}

}
