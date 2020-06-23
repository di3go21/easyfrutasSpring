package com.easyfrutas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyfrutas.model.Producto;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;
import com.easyfrutas.repositorios.VentaRepositorio;
import com.easyfrutas.servicios.UsuarioServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	@Autowired
	public ProductoRepositorio productoRepositorio;
	@Autowired
	UsuarioServicio usuServ;
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
	
	@GetMapping("/personal/cambiarPass")
	public String servirCambioPass() {
		
		return "formCambioPass";
		
	}
	@PostMapping("/personal/cambiarPass")
	public String cambiarContra(@RequestParam("vieja") String oldP, @RequestParam("nueva1") String newP1,
			 @RequestParam("nueva2") String newP2,Model modelo) {
		
		System.err.println(oldP);
		System.err.println(newP1);
		System.err.println(newP2);
		
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!newP1.contentEquals(newP2)) {
			modelo.addAttribute("mensaje","Las contraseñas no coinciden");
			return "formCambioPass";
			
		}
		
		
		
		
		if (email!=null) {
			
			Usuario loco = usuServ.cambiaPass(email, oldP, newP1);
			if (loco!=null) {
				modelo.addAttribute("mensaje","Se ha cambiado la contraseña satisfactoriamente!");
				return "cambiopass";
			}
			else
				modelo.addAttribute("mensaje","LOCO vacio No se ha podido cambiar la contraseña."
						+ " Intentelo otra vez y si el problema persiste pónganse en contacto con nosotros");
			
				
		}
		else
			modelo.addAttribute("mensaje","Email vacio No se ha podido cambiar la contraseña. "
					+ "Intentelo otra vez y si el problema persiste pónganse en contacto con nosotros");
		
		
		
		return "cambiopass";
	}
	
	
	
	@PostMapping("/personal/cambios")
	public String nuevosDatos(Model model,@ModelAttribute Usuario usuario) {
		System.err.println(usuario);
		System.err.println(model);
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario auxiliar= usuServ.buscarPorEmail(email);
		auxiliar.setNombre(usuario.getNombre());
		auxiliar.setApellido(usuario.getApellido());
		auxiliar.setTelefono(usuario.getTelefono());
		auxiliar.setDireccion(usuario.getDireccion());
		
		System.err.println(auxiliar);
		System.err.println(usuServ.guarda(auxiliar));
		
		model.addAttribute("usuario",auxiliar);
		
		return "redirect:/personal";
	}
	
	
	
	
	
	

}
