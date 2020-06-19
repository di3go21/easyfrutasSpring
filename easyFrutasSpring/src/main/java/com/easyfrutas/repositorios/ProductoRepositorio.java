package com.easyfrutas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyfrutas.model.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto	,Integer>{

}
