package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByProjetoId(Long projetoId);
}
