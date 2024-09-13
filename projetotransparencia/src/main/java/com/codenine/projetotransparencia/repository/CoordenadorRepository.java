package com.codenine.projetotransparencia.repository;


import com.codenine.projetotransparencia.entities.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
}

