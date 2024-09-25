package com.codenine.projetotransparencia.entities;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
    @Setter
    @Getter
    private String login;
    @Setter
    @Getter
    private String password;

	@Setter
    @Getter
    @Enumerated(EnumType.STRING)
	private AdminRole role;


    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == AdminRole.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	@Override
	public String getUsername() {
		return login;
	}

    //@Override
	//public boolean isAccountNonExpired() {
	//	return true; // Retorne true ou faça lógica de expiração
	//}

	//@Override
	//public boolean isAccountNonLocked() {
	//	return true; // Retorne true ou implemente lógica de bloqueio
	//}

	//@Override
	//public boolean isCredentialsNonExpired() {
	//	return true; // Retorne true ou implemente lógica para credenciais
	//}

	//@Override
	//public boolean isEnabled() {
	//	return true; // Retorne true se o administrador estiver habilitado
	//}


}
