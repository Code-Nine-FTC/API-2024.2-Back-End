package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.PrestacaoContas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestacaoContasRepository extends JpaRepository<PrestacaoContas, Long> {
    // Aqui você pode adicionar métodos personalizados se necessário
}