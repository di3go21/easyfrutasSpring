package com.easyfrutas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;


@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {
	
	public List<Venta> findByUsuario(Usuario usuario);
	public List<Venta> findByUsuarioEmail(String email);
	
	
//	public List<Venta> findByLoc();

}
