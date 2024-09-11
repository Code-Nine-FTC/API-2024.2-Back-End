package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
