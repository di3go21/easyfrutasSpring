package com.easyfrutas.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyfrutas.email.EmailService;
import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.servicios.UsuarioServicio;


@Controller
public class LoginController {
	@Autowired
	UsuarioServicio usuServ;
	@Autowired
	EmailService emailService;
	
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
	public String registroPost(@ModelAttribute Usuario usuario , @RequestParam("contrasenia2") String contrasenia2) {
		
		if(!contrasenia2.contentEquals(usuario.getContrasenia())) {
			
			return "redirect:/registro";
		}
	
			
		
		Usuario palo=usuServ.buscarPorEmail(usuario.getEmail());
		
		if (palo!=null) {
			System.err.println("EL USUARIO EXISTE");
			return "redirect:/registro";
			
		}
		usuario.setVerificado(true);// ESTO LO QUITAMOS
		usuServ.registrar(usuario);
		
		
		return "registro_hecho";
		
	}
	
	@GetMapping("/valida/{codigo}")
	public String acivalo(@PathVariable String codigo, Model modelo) {
		
		System.err.println("###########################################3");
		Usuario palo=usuServ.buscaPorCodigo(codigo);
		
	
		
		if (palo!=null) {
			if(palo.isVerificado()) {
				modelo.addAttribute("mensaje","El usuario con email "+palo.getEmail()+" ya está verificado.");
				return "revalidacion";
			}
			
			
			palo.setVerificado(true);
		    usuServ.guarda(palo);
		
		modelo.addAttribute("usuario",palo);
		
		
		return "activado";
			
		}
		
		
		return "redirect:/productos";
		
	}
	
	@PostMapping("/valida/otravez")
	public String reenvioVal(@RequestParam("email") String email, Model model) {
		
		System.err.println(email);
		Usuario usu= usuServ.buscarPorEmail(email);
		
		String mensaje;
		if(usu==null)
			mensaje="El usuario con email " +email+" no se encuentra en nuestra base de datos. Registrese otra vez o contacte con el soporte de la web.";
		else {
			
	
				if (!usu.isVerificado()) {
					usuServ.enviaEmailDeRegistro(usu);
					mensaje="Se ha reenviado el email con el link de validación a da direccion "+email+". Revise su correo electrónico";
					
				}else {
					mensaje="El usuario con email "+email+" ya está verificado. Utilice su contraseña para entrar "
							+ "o cambie su contraseña desde el formulario de login";
				}
		
		
		
		
		
		}
		
		model.addAttribute("mensaje",mensaje);
		
		
		return "revalidacion";
	}
	
	
}
