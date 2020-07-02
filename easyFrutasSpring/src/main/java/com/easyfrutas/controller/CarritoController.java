package com.easyfrutas.controller;

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
import com.easyfrutas.model.Usuario;
import com.easyfrutas.servicios.CarritoServicio;
import com.easyfrutas.servicios.ProductoServicio;
import com.easyfrutas.servicios.UsuarioServicio;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
	final String MENSAJE_VARIACION = "El stock ha variado y se han modificado algunas cantidades, por favor revisa la lista.";

	@Autowired
	UsuarioServicio usuarioServicio;
	@Autowired
	CarritoServicio carritoServicio;
	@Autowired
	ProductoServicio productoServicio;

	@GetMapping("")
	public String muestraCarrito(Model mod) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Carrito carr = carritoServicio.getCarritoUsuario(email);
		if (carr == null)
			return "carrito";
		if (!carr.getLista().isEmpty())
			if (carritoServicio.verificaCarrito(carr))
				mod.addAttribute("mensaje", this.MENSAJE_VARIACION);
		mod.addAttribute("carrito", carr);
		return "carrito";
	}

	@PostMapping("add/{id_producto}")
	public String loco(@PathVariable int id_producto, @RequestParam("cantidad") double cantidad) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usu = usuarioServicio.buscarPorEmail(email);
		Carrito carr = carritoServicio.getCarritoUsuario(email);
		carr = carritoServicio.agregaProducto(id_producto, cantidad, carr, usu);

		if (carr == null)
			return "error";

		return "redirect:/carrito";
	}

	@GetMapping("/actualiza/{id_producto}")
	public String actualizarProducto(@PathVariable int id_producto, @RequestParam("cantidad_nueva") double cantidad) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		
		carritoServicio.actualizar(email, id_producto, cantidad);

		return "redirect:/carrito";
	}

	@RequestMapping("/eliminar/{id_producto}")
	public String eliminarProducto(@PathVariable int id_producto) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		carritoServicio.eliminaProducto(email, id_producto);

		return "redirect:/carrito";
	}

}
