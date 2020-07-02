package com.easyfrutas.servicios;

import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Producto;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;

@Service
public class CarritoServicio {

	@Autowired
	CarritoRepositorio carritoRepositorio;
	@Autowired
	ProductoRepositorio prodRepositorio;
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	public Carrito getCarritoUsuario(String email) {
		return (carritoRepositorio.findByUsuarioEmail(email));
	}

	public boolean verificaCarrito(Carrito carr) {
		boolean hayCambios=false;
		if (carr != null) {

			for (Entry<Producto, Double> entry : carr.getLista().entrySet()) {
				if(entry.getKey().getStock()<0.5)
					carr.getLista().remove(entry.getKey());
				
				else if (entry.getKey().getStock() < entry.getValue()) {
					carr.getLista().put(entry.getKey(), entry.getKey().getStock());
					hayCambios=true;
				}
			}
		}
		carr.actualizaTotal();
		guardarCarrito(carr);
		return hayCambios;
	}

	public void eliminarCarrito(Carrito carr) {
		this.carritoRepositorio.delete(carr);

	}

	public Carrito guardarCarrito(Carrito carr) {
		return this.carritoRepositorio.save(carr);
	}

	public Carrito agregaProducto(int id_producto, double cantidad, Carrito carr, Usuario usu) {

		Producto prod = prodRepositorio.findById(id_producto).orElse(null);
		if (prod == null)
			return null;

		if (carr == null) {
			carr = new Carrito();
			carr.setLista(new HashMap<>());
			carr.setUsuario(usu);
			carr.getLista().put(prod, (cantidad));

		} else {
			if (carr.getLista().get(prod) == null) {
				carr.getLista().put(prod, (cantidad));
			} else {
				if (cantidad + (carr.getLista().get(prod)) > prod.getStock())
					carr.getLista().put(prod, prod.getStock());
				else
					carr.getLista().put(prod, (cantidad + (carr.getLista().get(prod))));
			}
		}
		carr.actualizaTotal();
		return carritoRepositorio.save(carr);

	}

	public Carrito actualizar(String email, int id_producto, double cantidad) {
		
		
		Carrito carr = carritoRepositorio.findByUsuarioEmail(email);
		Producto prod = prodRepositorio.findById(id_producto).orElse(null);
		if (prod == null)
			return null;
		carr.getLista().put(prod, cantidad);
		carr.actualizaTotal();

		return carritoRepositorio.save(carr);

	}

	public Carrito eliminaProducto(String email, int id_producto) {
		Carrito carr = carritoRepositorio.findByUsuarioEmail(email);
		Producto prod = prodRepositorio.findById(id_producto).orElse(null);

		if (prod == null)
			return null;

		carr.getLista().remove(prod);
		if (carr.getLista().isEmpty()) {
			carritoRepositorio.delete(carr);
			return null;
		}

		else
			carr.actualizaTotal();

		return carritoRepositorio.save(carr);
	}

	public Carrito arreglaCarrito(Carrito carr) {

		carr.getLista().forEach((prod, cantidad) -> {
			if (prod.getStock() == 0)
				carr.getLista().remove(prod);
			else if (cantidad > prod.getStock())
				carr.getLista().put(prod, prod.getStock());
		});
		carr.actualizaTotal();

		return carritoRepositorio.save(carr);

	}

}
