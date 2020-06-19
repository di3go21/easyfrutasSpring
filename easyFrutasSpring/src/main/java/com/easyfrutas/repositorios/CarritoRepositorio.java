package com.easyfrutas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;

public interface CarritoRepositorio extends JpaRepository<Carrito, Long> {
	
	public Carrito findByUsuario(Usuario usuario);
	

}
