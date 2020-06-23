package com.easyfrutas.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Producto;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;


@Controller
@RequestMapping("/carrito")
public class CarritoController {
	
	
	@Autowired
	UsuarioRepositorio usure;
	@Autowired
	CarritoRepositorio carrRe;
	@Autowired
	ProductoRepositorio prodRepo;
	
	
	
	
	@GetMapping("")
	public String muestraCarrito(Model mod) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usure.findByEmail(email);
		Carrito carr = carrRe.findByUsuario(usu);
				
		
		mod.addAttribute("carrito", carr);
		return "carrito";
		}
	
	
	@PostMapping("add/{loco}")
	public String loco(@PathVariable int loco,@RequestParam("cantidad") double cantidad, Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		System.err.println("este es el email autenticado "+email);
		Usuario usu = usure.findByEmail(email);
		System.err.println("ESTE EL USUARIO QUE RECUPERA");
		System.err.println(usu);
		System.err.println("ahora vamos a buscar el carrito");
		Carrito carr = carrRe.findByUsuario(usu);
		System.err.println(carr);
		
		if (carr==null)
		{
			carr=new Carrito();
			carr.setLista(new HashMap<>());
			Producto prod= prodRepo.findById(loco).orElse(null);
			carr.getLista().put(prod, cantidad);
			carr.setUsuario(usu);
			
			System.err.println("se ha guardado el producto "+prod+"con la cantidad "+cantidad);
		}else {
			
			System.out.println("SU CARRO NO ESTA VACIO");
			
			Producto prod= prodRepo.findById(loco).orElse(null);
			if(carr.getLista().get(prod) == null) {
				carr.getLista().put(prod, (cantidad));
				
			}else {
				carr.getLista().put(prod, (cantidad+(carr.getLista().get(prod))));
			}
				
			carr.setUsuario(usu);
			System.out.println(carr);
			
		}
		
		carr.actualizaTotal();
		
		carrRe.save(carr);
		
		
		model.addAttribute("carrito",carr);
		
		System.out.println("SE HA GUARDADO");
		
		
		return "carrito";
	}
	
	
	@GetMapping("/actualiza/{id}")
	public String actualizarProducto(@PathVariable int id,@RequestParam("cantidad_nueva") double cantidad){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usure.findByEmail(email);
		Carrito carr = carrRe.findByUsuario(usu);
		Producto prod = prodRepo.findById(id).orElse(null);
		carr.getLista().put(prod, cantidad);
		carr.actualizaTotal();
		
		carrRe.save(carr);
		
		return "redirect:/carrito";
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable int id) {
		
		System.err.println("vamos a borrar el producto "+id);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usure.findByEmail(email);
		Carrito carr = carrRe.findByUsuario(usu);
		System.err.println("carrito antes "+carr);
		Producto prod = prodRepo.findById(id).orElse(null);
		carr.actualizaTotal();
		
		carr.getLista().remove(prod);
		carr.actualizaTotal();
		

		System.err.println("carrito despues "+carr);
		carrRe.save(carr);

		System.err.println("BOORRADO EL CARRITO");
		
		return "redirect:/carrito";
	}
	


}
