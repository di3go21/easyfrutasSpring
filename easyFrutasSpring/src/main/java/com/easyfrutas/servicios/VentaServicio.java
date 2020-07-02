package com.easyfrutas.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;
import com.easyfrutas.repositorios.VentaRepositorio;

@Service
public class VentaServicio {

	@Autowired
	public VentaRepositorio ventaRepositorio;
	
	public List<Venta> ventasDeUsuario(Usuario usuario){
		return ventaRepositorio.findByUsuarioEmail(usuario.getEmail());
	}
	
}
