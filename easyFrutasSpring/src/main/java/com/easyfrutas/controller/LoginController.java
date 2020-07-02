package com.easyfrutas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.easyfrutas.email.EmailService;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.servicios.UsuarioServicio;

@Controller
public class LoginController implements WebMvcConfigurer {

	final String ERROR_VALIDACION = "Hubo un error con alguno de los datos. Por favor rellene otra vez el formulario y si el problema persiste contacte con nosotros.";
	final String ERROR_PASS_DISTINTAS = "Las contraseñas no coinciden";
	final String USU_YA_VERIF = "El usuario con email %s ya está verificado.";
	final String USU_YA_REGIS = "El usuario con email %s ya está registrado.";


	@Autowired
	UsuarioServicio usuServ;
	@Autowired
	EmailService emailService;

	@GetMapping("/login")
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/productos";
		}
		return "login";
	}

	@GetMapping("/registro")
	public String registro(Model model) {

		model.addAttribute("usuario", new Usuario());
		return "registro";

	}

	@PostMapping("/registro")
	public String registroPost(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult,
			@RequestParam("contrasenia2") String contrasenia2, Model model) {

		if (bindingResult.hasErrors()) {
			// TODO
			/*
			 * HAY QUE CODIFICAR LOS ERRORES PARA CADA CAMPO
			 */

			model.addAttribute("mensaje", this.ERROR_VALIDACION);
			return "registro";
		}

		if (!contrasenia2.contentEquals(usuario.getContrasenia())) {
			model.addAttribute("mensaje", ERROR_PASS_DISTINTAS);
			return "registro";
		}
		/*
		 * FALTAN LAS VALIDACIONES DE LOS DEMAS CAMPOS
		 * 
		 * 
		 */

		Usuario palo = usuServ.buscarPorEmail(usuario.getEmail());
		if (palo != null) {
			model.addAttribute("mensaje", String.format(USU_YA_REGIS, usuario.getEmail()));
			return "registro";
		}

		usuServ.registrar(usuario);

		return "registro_hecho";

	}

	

}
