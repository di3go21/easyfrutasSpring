package com.easyfrutas.servicios;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.easyfrutas.email.EmailService;
import com.easyfrutas.email.Mail;
import com.easyfrutas.model.Carrito;
import com.easyfrutas.model.Usuario;
import com.easyfrutas.repositorios.CarritoRepositorio;
import com.easyfrutas.repositorios.UsuarioRepositorio;
import com.easyfrutas.util.Codificador;

@Service
public class UsuarioServicio {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	CarritoRepositorio carritoRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public Usuario findById(long id) {
		return usuarioRepositorio.findById(id).orElse(null);
	}

	public Usuario buscarPorEmail(String email) {
		return usuarioRepositorio.findFirstByEmail(email);

	}

	public Usuario cambiaPass(String email, String vieja, String nueva) {
		Usuario loco = buscarPorEmail(email);
		loco.setContrasenia(passEncoder.encode(nueva));
		loco = usuarioRepositorio.save(loco);

		return loco;

	}

	public Usuario registrar(Usuario u) {

		u.setContrasenia(passEncoder.encode(u.getContrasenia()));
		u.setCodigoValidacion(Codificador.codifica(u.getEmail()));
		u.setVerificado(true);
		Usuario guardado = usuarioRepositorio.save(u);
		Carrito carr = new Carrito();
		carr.setUsuario(u);
		carr.setLista(new HashMap<>());
		carritoRepo.save(carr);

		return guardado;
	}

	public void enviaEmailDeRegistro(Usuario u) {

		Mail mail = new Mail();
		mail.setFrom("easyfrutas@gmail.com");
		mail.setTo(u.getEmail());
		mail.setSubject("Confirma Tu Registro en EasyFrutas");
		mail.setNombreCompleto(u.getNombre() + ' ' + u.getApellido());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nombre", mail.getNombreCompleto());
		model.put("codigo", u.getCodigoValidacion());
		mail.setModel(model);

		try {
			emailService.sendSimpleMessage(mail);
		} catch (MessagingException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Usuario buscaPorCodigo(String codigo) {
		return this.usuarioRepositorio.findByCodigoValidacion(codigo);
	}

	public Usuario guarda(Usuario usu) {
		return usuarioRepositorio.save(usu);
	}

	public boolean passAntiguaValida(String passAntigua, String email) {
		Usuario usuario = usuarioRepositorio.findByEmail(email);
		
		return passEncoder.matches(passAntigua, usuario.getContrasenia());
	}

	public Usuario actualizaDatos(Usuario usuario, String email) {
		Usuario auxiliar = this.buscarPorEmail(email);
		auxiliar.setNombre(usuario.getNombre());
		auxiliar.setApellido(usuario.getApellido());
		auxiliar.setTelefono(usuario.getTelefono());
		auxiliar.setDireccion(usuario.getDireccion());
		this.guarda(auxiliar);
		return auxiliar;
	}

	public void validaUsuario(String email) {
		Usuario usu = usuarioRepositorio.findByEmail(email);
		usu.setVerificado(true);
		usuarioRepositorio.save(usu);
		
	}

}