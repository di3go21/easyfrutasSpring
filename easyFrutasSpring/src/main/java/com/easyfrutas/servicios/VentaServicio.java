package com.easyfrutas.servicios;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.easyfrutas.controller.errors.ProductoSinStockException;
import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.PrecioCoste;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.model.Venta;
import com.easyfrutas.repositorios.PrecioCosteRepositorio;
import com.easyfrutas.repositorios.ProductoRepositorio;
import com.easyfrutas.repositorios.VentaRepositorio;
import com.easyfrutas.util.Numeros;

@Service
public class VentaServicio {

	@Autowired
	public VentaRepositorio ventaRepositorio;
	@Autowired
	public PrecioCosteRepositorio precioCosteRepo;
	@Autowired
	public ProductoRepositorio productoRepositorio;
	
	public List<Venta> ventasDeUsuario(Usuario usuario){
		return ventaRepositorio.findByUsuarioEmail(usuario.getEmail());
	}
	
	public Venta nuevaVenta() {
		return null;
	}
	
	public Venta guardaVenta(Venta venta) {return ventaRepositorio.save(venta);}	
	
	public Venta procesaVenta(Usuario usu,Carrito carr) {
		Venta venta=new Venta();		
		venta.setArticulosPrecio(new HashMap<>());
		carr.getLista().forEach((prod,cantidad)->{
			PrecioCoste precioCoste;
			double coste= prod.getPrecio()*cantidad;
			coste = Numeros.redondeaAdos(coste);
			precioCoste=new PrecioCoste();
			precioCoste.setPrecioInstante(prod.getPrecio());
			precioCoste.setCoste(coste);
			
			if((prod.getStock()-cantidad)<0)
				//throw new HttpServerErrorException(HttpStatus.NOT_ACCEPTABLE);
				throw new ProductoSinStockException(carr);
			precioCoste=precioCosteRepo.save(precioCoste);
			prod.setStock(Numeros.redondeaAdos(prod.getStock()-cantidad));
			
		productoRepositorio.save(prod);
			venta.getArticulosPrecio().put(prod,precioCoste);
		});
		
		venta.setUsuario(usu);
		venta.setPrecioTotal(carr.getTotal());
		venta.setDireccion(usu.getDireccion());
		
		return venta;
	}
}
