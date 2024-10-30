package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import com.codenine.projetotransparencia.repository.AuditoriaRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private AuditoriaRepositoryCustomImpl auditoriaRepositoryCustom;


    public List<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }

    // Método para buscar auditoria por ID
    public Optional<Auditoria> buscarAuditoriaPorId(Long id) {
        return auditoriaRepository.findById(id);
    }

    public List<Auditoria> buscarPorTipo(String tipoAuditoria) {
        return auditoriaRepository.findByTipoAuditoria(tipoAuditoria);
    }

    public List<Auditoria> buscarPorReferencia(String referenciaProjeto) {
        return auditoriaRepository.findByReferenciaProjeto(referenciaProjeto);
    }

    public List<Auditoria> buscarPorTipoEReferencia(String tipoAuditoria, String referenciaProjeto) {
        return auditoriaRepository.findByTipoAuditoriaAndReferenciaProjeto(tipoAuditoria, referenciaProjeto);
    }
}
