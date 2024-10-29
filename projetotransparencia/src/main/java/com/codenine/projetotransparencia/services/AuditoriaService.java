package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }

    // Método para buscar auditoria por ID
    public Optional<Auditoria> buscarAuditoriaPorId(Long id) {
        return auditoriaRepository.findById(id);
    }
}
