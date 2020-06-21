package com.easyfrutas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;

@Repository
public interface CarritoRepositorio extends JpaRepository<Carrito, Long> {
	
	public Carrito findByUsuario(Usuario usuario);
	

}
