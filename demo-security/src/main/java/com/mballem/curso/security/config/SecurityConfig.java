package com.mballem.curso.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mballem.curso.security.service.MedicoService;
import com.mballem.curso.security.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * Configuração para trabalhar com security, aqui o proprio security está
	 * bloqueando o css da pagina e outros para que não seja acessado sem estarmos
	 * na url correta que seria a inicial "home".
	 * 
	 * Estamos liberando os webjars/ o "**" é para liberar tudo que vem depois
	 * daquela barra.
	 * 
	 * Aqui também estamos fazendo a validação de senhas, e fazendo a criptografia
	 * delas.
	 * 
	 */

	@Autowired
	private UsuarioService service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Liberando o acesso e os formatos de paginas imagens etc.

		http.authorizeRequests().antMatchers("/webjars/**", "/css/**", "/image/**", "/js/** ").permitAll()
				.antMatchers("/", "/home").permitAll()

				// Estamos definido as permiçoes, cada usuario que conter, exemplo /u/alguma
				// coisa
				// so vai pode acessar aquele perfil e não o do outro que seria medico/...

				// ACESSOS PRIVADOS ADMIN
				.antMatchers("/u/editar/senha", "/u/confirmar/senha").hasAnyAuthority("MEDICO,PACIENTE")
				.antMatchers("/u/**").hasAuthority("ADMIN")

				// ACESSOS PRIVADOS MEDICOS
				.antMatchers("/medicos/dados", "/medicos/salvar", "/medicos/editar").hasAnyAuthority("MEDICO,ADMIN")
				.antMatchers("/medicos/**").hasAuthority("MEDICO")

				// ACESSOS PRIVADOS PACIENTE
				.antMatchers("/paciente/**").hasAuthority("PACIENTE")
  
				// ACESSOS PRIVADOS ESPECIALIDADES
				.antMatchers("/especialidades/datatables/server/medico/*").hasAnyAuthority("MEDICO,ADMIN")
				.antMatchers("/especialidades/titulo").hasAnyAuthority("MEDICO,ADMIN,PACIENTE")
				.antMatchers("/especialidades/**").hasAuthority("ADMIN")

				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.failureUrl("/login-error").permitAll().and().logout().logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/acesso-negado");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

}
