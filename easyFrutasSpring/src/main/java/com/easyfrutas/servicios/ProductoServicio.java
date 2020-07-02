package com.easyfrutas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfrutas.model.Producto;
import com.easyfrutas.repositorios.ProductoRepositorio;

@Service
public class ProductoServicio {

	@Autowired
	public ProductoRepositorio productoRepositorio;
	
	
	public List<Producto> encuentraTodos(){
		return this.productoRepositorio.findAll();
	}
	
	public Optional<Producto> encuentraPorId(int id){
		return this.productoRepositorio.findById(id);
	}
	
}
