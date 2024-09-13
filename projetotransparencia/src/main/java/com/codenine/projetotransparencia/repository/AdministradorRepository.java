package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}

