package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Projeto;

import java.util.List;
import java.util.Optional;

public interface ProjetoRepositoryCustom {
    List<Projeto> findAll();
    Optional<Projeto> findActiveById(Long id);
}
