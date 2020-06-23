package com.easyfrutas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfrutas.model.PrecioCoste;
@Repository
public interface PrecioCosteRepositorio extends JpaRepository<PrecioCoste	, String>{

}
