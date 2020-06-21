package com.easyfrutas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;
import com.easyfrutas.repositorios.VentaRepositorio;

@Controller
public class CompraController {
	

	@Autowired
	UsuarioRepositorio usure;
	@Autowired
	CarritoRepositorio carrRe;
	@Autowired
	ProductoRepositorio prodRepo;
	@Autowired
	VentaRepositorio ventaRepo;
	
	@GetMapping("/compra")
	public String getResumen(Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usure.findByEmail(email);
		Carrito carr = carrRe.findByUsuario(usu);
		
		
		if (carr==null)
			return "redirect:/productos";
		
		if (carr.getLista().isEmpty())
			return "redirect:/productos";
		
		model.addAttribute("carrito", carr);
		model.addAttribute("usuario",usu);
		
		return "precompra";
		
	}
	
	
	@PostMapping("/compra")
	public String efectuarCompra(Model model){
		
		System.out.println("ENTRAMOS A LA COMPRA");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usure.findByEmail(email);
		Carrito carr = carrRe.findByUsuario(usu);
		Venta venta= new Venta();
		venta.setArticulosPrecio(new HashMap<>());
		
		
		
		
		carr.getLista().forEach((prod,cantidad)->{
			
			double coste= prod.getPrecio()*cantidad;
			System.err.println(coste+ " coste antes del redondeo");
			coste = (double) Math.round(coste * 100) / 100;
			System.err.println(coste+ " coste despues del redondeo");
			
			venta.getArticulosPrecio().put(prod,coste);
			System.err.println("se ha añadido el producto "+prod.getNombre()+" con el coste "+coste);
			
		});
		
		venta.setUsuario(usu);
		venta.setPrecioTotal(carr.getTotal());
		Venta compraHecha=ventaRepo.save(venta);
		model.addAttribute("venta", compraHecha);
		model.addAttribute("carrito", carr);
		model.addAttribute("usuario",usu);
		
		carrRe.delete(carr);
		
		System.err.println("COMPRA HECHA HECHO");
		
		
		return "resumenCompra";
	}
	
		
		
		
		
	}
	
	
	
