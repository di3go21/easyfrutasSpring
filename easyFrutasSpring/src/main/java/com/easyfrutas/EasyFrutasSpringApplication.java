package com.easyfrutas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.easyfrutas.model.Usuario;
import com.easyfrutas.servicios.UsuarioServicio;

@SpringBootApplication
public class EasyFrutasSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyFrutasSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(UsuarioServicio usuarioServicio) {
		return (args)->{
			
//			Usuario usu = new Usuario(2, "Juan diego", "Leiva Contreras", LocalDate.of(1990, 9, 16));
//			Usuario usu2 = new Usuario(2, "Aludena Asenjo", "Tapia", LocalDate.of(1993, 8, 9));
//			repositorio.saveAll(Arrays.asList(usu,usu2));
			Usuario usu = new Usuario();
			usu.setNombre("Juan diego");
			usu.setApellido("Leiva Contreras");
			usu.setContrasenia("password");
			usu.setEmail("di3go21@gmail.com");
			usu.setTelefono("916130484");
			usu.setDireccion("corona verde ");
			
			Usuario usu2 = new Usuario();
			usu2.setNombre("Almu diego");
			usu2.setApellido("Asenjo tapia");
			usu2.setContrasenia("password");
			usu2.setEmail("hola@test2");
			usu2.setTelefono("916130484");
			usuarioServicio.registrar(usu);
			usuarioServicio.registrar(usu2);

//			Usuario usuario = new Usuario();
//			usuario.setEmail("abcdefg");
//			usuario.setContrasenia("123456");

//			usuarioServicio.registrar(usuario);
};
}}
