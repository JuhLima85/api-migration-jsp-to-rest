/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devsibre.ConfigWebSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Convidado
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementsUserDetaisService userDetailsServer;

	@Override // Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // Desativa as configurações padrão de memória
				.authorizeRequests() // Permite restringir acesso
				.antMatchers(HttpMethod.GET,"/").permitAll() // Qualquer usuário acessa a página inicial
				.antMatchers(HttpMethod.GET, "/agendas_User").permitAll() // permite qualquer usuário a esta pagina em especifico
				.antMatchers(HttpMethod.GET, "/agendas_User/{id}").permitAll() // permite qualquer usuário a esta pagina em especifico
				.antMatchers(HttpMethod.POST, "/agendas_User").permitAll() // permite qualquer usuário a esta pagina em especifico
				.antMatchers(HttpMethod.GET, "/edita_Cadastro").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/newagenda").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/newagenda").hasRole("ADMIN") // permite apenas perfis usuario1
				.antMatchers(HttpMethod.POST, "/agendas").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/agendas").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/listarcadastro").hasRole("ADMIN") // permite apenas perfis administrador
				.antMatchers(HttpMethod.POST,"/listarcadastro").hasRole("ADMIN") // permite apenas perfis administrador
				.antMatchers(HttpMethod.GET,"/lista_patrimonio").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/listacantina").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST,"/listacantina").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/familia").permitAll()
				//.antMatchers(HttpMethod.POST,"/listacantina").hasRole("USER")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/entrar").permitAll() // página padrão se efetuou o login
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/entrar"); // página padrão após fazer o logout

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServer).passwordEncoder(new BCryptPasswordEncoder());
	}

}
