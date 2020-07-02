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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.easyfrutas.email.EmailService;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.servicios.UsuarioServicio;

@Controller
public class LoginController implements WebMvcConfigurer {
	
	final String ERROR_VALIDACION="Hubo un error con alguno de los datos. Por favor rellene otra vez el formulario y si el problema persiste contacte con nosotros.";
	final String ERROR_PASS_DISTINTAS="Las contraseñas no coinciden";
	final String USU_YA_VERIF="El usuario con email %s ya está verificado.";
	final String USU_YA_REGIS="El usuario con email %s ya está registrado.";
	
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

			model.addAttribute("mensaje",this.ERROR_VALIDACION);
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

	@GetMapping("/valida/{codigo}")
	public String acivalo(@PathVariable String codigo, Model modelo) {

		System.err.println("###########################################3");
		Usuario palo = usuServ.buscaPorCodigo(codigo);

		if (palo != null) {
			if (palo.isVerificado()) {
				modelo.addAttribute("mensaje", String.format(this.USU_YA_VERIF, palo.getEmail()));
				return "revalidacion";
			}

			palo.setVerificado(true);
			usuServ.guarda(palo);

			modelo.addAttribute("usuario", palo);

			return "activado";

		}

		return "redirect:/productos";

	}

	@PostMapping("/valida/otravez")
	public String reenvioVal(@RequestParam("email") String email, Model model) {

		System.err.println(email);
		Usuario usu = usuServ.buscarPorEmail(email);

		String mensaje;
		if (usu == null)
			mensaje = "El usuario con email " + email
					+ " no se encuentra en nuestra base de datos. Registrese otra vez o contacte con el soporte de la web.";
		else {

			if (!usu.isVerificado()) {
				usuServ.enviaEmailDeRegistro(usu);
				mensaje = "Se ha reenviado el email con el link de validación a da direccion " + email
						+ ". Revise su correo electrónico";

			} else {
				mensaje = "El usuario con email " + email + " ya está verificado. Utilice su contraseña para entrar "
						+ "o cambie su contraseña desde el formulario de login";
			}

		}

		model.addAttribute("mensaje", mensaje);

		return "revalidacion";
	}

}
