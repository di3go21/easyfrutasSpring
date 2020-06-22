package com.easyfrutas.servicios;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

	public Usuario registrar(Usuario u) {

		u.setContrasenia(passEncoder.encode(u.getContrasenia()));
		u.setCodigoValidacion(Codificador.codifica(u.getEmail()));
	
		Usuario guardado = usuarioRepositorio.save(u);
		enviaEmailDeRegistro(u);

		Carrito carr = new Carrito();
		carr.setUsuario(u);
		carr.setLista(new HashMap<>());
		carritoRepo.save(carr);

		return guardado;
	}

	private void enviaEmailDeRegistro(Usuario u) {

		Mail mail = new Mail();
		mail.setFrom("noReply@gmail.com");
		mail.setTo(u.getEmail());
		mail.setSubject("Confirma Tu Registro en EasyFrutas");
		mail.setNombreCompleto(u.getNombre()+' '+u.getApellido());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nombre", mail.getNombreCompleto());
		model.put("codigo", u.getCodigoValidacion());
		System.err.println("el codigo de validacion del email va a ser "+u.getCodigoValidacion());
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
	
	public void guarda(Usuario usu) {
		usuarioRepositorio.save(usu);
	}

}