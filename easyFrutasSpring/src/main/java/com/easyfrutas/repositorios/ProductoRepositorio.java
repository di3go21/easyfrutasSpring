package com.easyfrutas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfrutas.model.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto	,Integer>{
	
	public List<Producto> findByNombreContainsIgnoreCase(String string);

}
