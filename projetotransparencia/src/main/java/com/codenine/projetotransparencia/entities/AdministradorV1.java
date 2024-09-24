package com.codenine.projetotransparencia.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "administrador")
@Entity(name = "administrador")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AdministradorV1 implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String login;
	private String password;
	private AdminRole role;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == AdminRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else return	List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}
	public void setLogin(String login) {
	    this.login = login;
	}

	public void setPassword(String password) {
	    this.password = password;
	}

	public void setRole(AdminRole role) {
	    this.role = role;
	}

	

}
