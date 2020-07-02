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
import com.easyfrutas.servicios.ProductoServicio;
import com.easyfrutas.servicios.UsuarioServicio;
import com.easyfrutas.servicios.VentaServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {
	final String NO_COINCIDE_ACTUAL = "La contraseña actual no coincide con la que se ha introducido";
	final String IGUALES_TODAS = "La contraseña nueva y la antigua no pueden ser iguales";
	final String NO_COINCIDEN_NUEVAS = "Las contraseñas no coinciden";
	final String CAMBIO_OK = "Se ha cambiado la contraseña satisfactoriamente!";
	final String ERROR = "No se ha podido cambiar la contraseña."
			+ " Intentelo otra vez y si el problema persiste pónganse en contacto con nosotros";
	final String MENSAJE = "mensaje";

	@Autowired
	public UsuarioServicio usuarioServicio;

	@Autowired
	public ProductoServicio productoServicio;

	@Autowired
	UsuarioServicio usuServ;
	@Autowired
	public VentaServicio ventaServicio;

	@GetMapping("/hola")
	public String bienvenida(Model modelo) {
		return "welcome";
	}

	@GetMapping({ "", "/productos" })
	public String productos(Model modelo) {
		List<Producto> todosProductos = productoServicio.encuentraTodos();
		modelo.addAttribute("productos", todosProductos);
		return "productos";
	}

	@GetMapping("/personal")
	public String areaPersonal(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usuarioServicio.buscarPorEmail(email);
		List<Venta> listaVentas = ventaServicio.ventasDeUsuario(usu);
		model.addAttribute("ventas", listaVentas);
		model.addAttribute("usuario", usu);
		return "personal";
	}

	@GetMapping("/personal/cambiarPass")
	public String servirCambioPass() {
		return "formCambioPass";
	}

	@PostMapping("/personal/cambiarPass")
	public String cambiarContra(@RequestParam("vieja") String oldP, @RequestParam("nueva1") String newP1,
			@RequestParam("nueva2") String newP2, Model modelo) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		if (newP1.contentEquals(newP2) & newP1.contentEquals(oldP)) {
			modelo.addAttribute(this.MENSAJE, this.IGUALES_TODAS);
			return "formCambioPass";
		}

		if (!newP1.contentEquals(newP2)) {
			modelo.addAttribute(this.MENSAJE, this.NO_COINCIDEN_NUEVAS);
			return "formCambioPass";
		}

		if (!usuarioServicio.passAntiguaValida(oldP, email)) {
			modelo.addAttribute(this.MENSAJE, this.NO_COINCIDE_ACTUAL);
			return "formCambioPass";
		}

		if (email != null) {
			Usuario loco = usuServ.cambiaPass(email, oldP, newP1);
			if (loco != null) {
				modelo.addAttribute(this.MENSAJE, this.CAMBIO_OK);
				return "cambiopass";
			}
		}

		modelo.addAttribute(this.MENSAJE, this.ERROR);

		return "formCambioPass";
	}

	@PostMapping("/personal/cambios")
	public String nuevosDatos(Model model, @ModelAttribute Usuario usuario) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario nuevo =usuServ.actualizaDatos(usuario,email);
	

		model.addAttribute("usuario", nuevo);

		return "redirect:/personal";
	}

}
