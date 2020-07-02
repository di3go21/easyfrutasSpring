package com.easyfrutas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;
import com.easyfrutas.servicios.CarritoServicio;
import com.easyfrutas.servicios.UsuarioServicio;
import com.easyfrutas.servicios.VentaServicio;

@Controller
public class CompraController {
	final String ERRORMSG = "Ha ocurrido un error interno en la aplicación. Vuelva a intentar la operación y si el problema persiste"
			+ " por favor notifíquelo."; 
	final String MENSAJE_VARIACION="El stock ha variado y se han modificado algunas cantidades, por favor revisa la lista.";
	
	@Autowired
	UsuarioServicio usuarioServicio;
	@Autowired
	CarritoServicio carritoServicio;
	@Autowired
	VentaServicio ventaServicio;
	
	@GetMapping("/compra")
	public String getResumen(Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usuarioServicio.buscarPorEmail(email);
		Carrito carr =carritoServicio.getCarritoUsuario(email);
		
		if (carr==null)
			return "redirect:/productos";
		if (carritoServicio.verificaCarrito(carr)) 
			model.addAttribute("mensaje", this.MENSAJE_VARIACION);
		
		if (carr.getTotal()==0.0)
			carritoServicio.eliminarCarrito(carr);
		model.addAttribute("carrito", carr);
		model.addAttribute("usuario",usu);
		return "precompra";
		
	}
	
	
	@PostMapping("/compra")
	public String efectuarCompra(Model model){
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usuarioServicio.buscarPorEmail(email);
		Carrito carr = carritoServicio.getCarritoUsuario(email);
		Venta compraHecha= ventaServicio.guardaVenta(ventaServicio.procesaVenta(usu,carr));
			
		if (compraHecha==null) {
			model.addAttribute("mensaje",this.ERRORMSG);
			return "redirect:/productos";
		}
		model.addAttribute("venta", compraHecha);
		model.addAttribute("carrito", carr);
		model.addAttribute("usuario",usu);
		carritoServicio.eliminarCarrito(carr);		
		return "resumenCompra";
	}
		
		
	}
	
	
	

