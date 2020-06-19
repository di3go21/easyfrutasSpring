package com.easyfrutas.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.UsuarioRepositorio;
import com.easyfrutas.servicios.UsuarioServicio;

@Service
public class UserDatailsServiceImpl implements UserDetailsService {

	@Autowired
	UsuarioServicio usuarioServicio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioServicio.buscarPorEmail(username);
		
		UserBuilder builder = null;

		if (usuario != null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(usuario.getContrasenia());
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));

			return builder.build();
		} else {
			System.err.println("no exiuste");
			throw new UsernameNotFoundException("usuario no existente");
		}

	}
	
	

}
