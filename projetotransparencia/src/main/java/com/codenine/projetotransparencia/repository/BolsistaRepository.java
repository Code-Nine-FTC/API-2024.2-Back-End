package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Bolsista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BolsistaRepository extends JpaRepository<Bolsista, Long> {
}
