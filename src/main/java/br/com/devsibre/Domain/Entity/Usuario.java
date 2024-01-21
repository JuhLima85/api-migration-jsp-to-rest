package br.com.devsibre.Domain.Entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.devsibre.Domain.Entity.Roles;

@Entity
public class Usuario implements UserDetails{

	 @Id
	private String login;
	private String email;
	private String senha;

	@ManyToMany
	@JoinTable(
	        name = "usuarios_roles",
	        joinColumns = @JoinColumn(
	          name = "usuario_id", referencedColumnName = "login"),
	        inverseJoinColumns = @JoinColumn(
	          name = "role_id", referencedColumnName = "nomeRole"))
	private List<Roles>roles;

	public Usuario() {

	}

	public Usuario(String login, String email, String senha, List<br.com.devsibre.Domain.Entity.Roles> roles) {
		super();
		this.login = login;
		this.email = email;
		this.senha = senha;
		this.roles = roles;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return (Collection<? extends GrantedAuthority>) this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		 return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		 return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		 return true;
	}

	@Override
	public boolean isEnabled() {
		 return true;
	}


}
