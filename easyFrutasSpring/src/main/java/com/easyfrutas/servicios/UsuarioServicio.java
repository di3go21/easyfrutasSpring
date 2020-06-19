package com.easyfrutas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

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

		return usuarioRepositorio.save(u);
	}
}