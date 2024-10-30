package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    List<Auditoria> findByTipoAuditoria(String tipoAuditoria);

    List<Auditoria> findByReferenciaProjeto(String referenciaProjeto);

    List<Auditoria> findByTipoAuditoriaAndReferenciaProjeto(String tipoAuditoria, String referenciaProjeto);
}
