package com.codenine.projetotransparencia.repository;


import com.codenine.projetotransparencia.entities.AdministradorV1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorV1, String> {
	 UserDetails findByLogin(String login);
}

