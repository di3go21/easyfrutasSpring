package com.easyfrutas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfrutas.model.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto	,Integer>{

}
