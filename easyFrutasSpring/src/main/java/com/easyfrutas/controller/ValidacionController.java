package com.easyfrutas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easyfrutas.email.EmailService;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.servicios.UsuarioServicio;

@Controller
public class ValidacionController {
	
	final String NO_REGISTRADO = "El usuario con email %s no se encuentra en nuestra base de datos. Regístrate otra vez o contacte con el soporte de la web.";
	final String REENVIADO = "Se ha reenviado el email con el link de validación a la direccion %s, revise su correo electrónico";
	final String YA_VERIFICADO = "El usuario con email %s ya está verificado. Utilice su contraseña para entrar "
			+ "o cambie su contraseña desde el formulario de login";

	@Autowired
	UsuarioServicio usuServ;
	@Autowired
	EmailService emailService;
	
	@GetMapping("/info/validacion")
	public String infoValidacion() {		return "infovalidacion";	}
	
	@GetMapping("/valida/{codigo}")
	public String acivalo(@PathVariable String codigo, Model modelo) {

		Usuario palo = usuServ.buscaPorCodigo(codigo);
		if (palo != null) {
			if (palo.isVerificado()) {
				modelo.addAttribute("mensaje", String.format(this.YA_VERIFICADO, palo.getEmail()));
				return "msg_validacion";
			}
			usuServ.validaUsuario(palo.getEmail());
			modelo.addAttribute("usuario", palo);
			return "activado";

		}

		return "redirect:/productos";

	}

	@PostMapping("/valida/otravez")
	public String reenvioVal(@RequestParam("email") String email, Model model) {

		Usuario usu = usuServ.buscarPorEmail(email);
		String mensaje;
		if (usu == null)
			mensaje = String.format(this.NO_REGISTRADO, email);
		else {
			if (!usu.isVerificado()) {
				usuServ.enviaEmailDeRegistro(usu);
				mensaje = String.format(this.REENVIADO, email);
			} else {
				mensaje = String.format(this.YA_VERIFICADO, email);
			}

		}
		model.addAttribute("mensaje", mensaje);

		return "msg_validacion";
	}

}
