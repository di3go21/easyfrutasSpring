package com.easyfrutas.servicios;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	CarritoRepositorio carritoRepo;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public Usuario findById(long id) {
		return usuarioRepositorio.findById(id).orElse(null);
	}

	public Usuario buscarPorEmail(String email) {
		return usuarioRepositorio.findFirstByEmail(email);

	}

	public Usuario registrar(Usuario u) {

		u.setContrasenia(passEncoder.encode(u.getContrasenia()));
		Usuario guardado= usuarioRepositorio.save(u);
		
		Carrito carr = new Carrito();
		carr.setUsuario(u);
		carr.setLista(new HashMap<>());
		carritoRepo.save(carr);
			
		return guardado;
	}
}