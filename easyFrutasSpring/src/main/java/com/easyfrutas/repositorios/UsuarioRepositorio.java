package com.easyfrutas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfrutas.model.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	
	public Usuario findFirstByEmail(String email);
	
	public Usuario findByEmail(String email);
	
	public Usuario findByCodigoValidacion(String codigoValidacion);


}
