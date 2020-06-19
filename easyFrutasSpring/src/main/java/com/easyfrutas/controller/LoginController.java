package com.easyfrutas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.easyfrutas.model.Usuario;
import com.easyfrutas.servicios.UsuarioServicio;

@Controller
public class LoginController {
	@Autowired
	UsuarioServicio usuServ;
	

	@GetMapping("/login")
	public String login() {
		
		System.out.println("hola");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			 return "redirect:/productos";
			
		}
//
//		    /* The user is logged in :) */
//		    return ("home");
//		}
		
		
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		
		model.addAttribute("usuario",new Usuario());
		
		return "registro";
		
	}
	
	@PostMapping("/registro")
	public String registroPost(@ModelAttribute Usuario usuario) {
		
		Usuario palo=usuServ.buscarPorEmail(usuario.getEmail());
		
		if (palo!=null) {
			System.err.println("EL USUARIO EXISTE");
			return "registro";
			
		}
		usuServ.registrar(usuario);
			
		return "login";
		
	}
	
	
}
