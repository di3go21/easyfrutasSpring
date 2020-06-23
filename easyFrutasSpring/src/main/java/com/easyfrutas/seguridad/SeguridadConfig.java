package com.easyfrutas.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
@Configuration
@EnableWebSecurity
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

	
//	
	@Autowired
	UserDatailsServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/","/static/**","/buscar/**","/info/**","/img/**","/webjars/**","/css/**","/public/**","/auth/**","/productos")
		.permitAll()
		.antMatchers("/login","/registro","/valida/**").permitAll()
		.antMatchers("/registration").permitAll()
		.anyRequest().authenticated().and().csrf().disable()
		.formLogin().loginPage("/login").defaultSuccessUrl("/productos")
		.usernameParameter("username")
		.passwordParameter("password").and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/productos")
		.and().exceptionHandling().accessDeniedPage("/access-denied");
		
//		.anyRequest().authenticated()
//		.and()
//	.formLogin()
//		.loginPage("/auth/login")
//		.defaultSuccessUrl("/public/index",true)
//
//		.loginProcessingUrl("/auth/login-post")
//		.permitAll()
//		.and()
//	.logout()
//		.logoutUrl("/auth/logout")
//		.logoutSuccessUrl("/public/index.html");
	
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
