package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.services.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auditorias")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping
    public List<Auditoria> listarAuditorias() {
        return auditoriaService.listarAuditorias();
    }

    // Endpoint para buscar auditoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> buscarAuditoriaPorId(@PathVariable Long id) {
        Optional<Auditoria> auditoria = auditoriaService.buscarAuditoriaPorId(id);
        if (auditoria.isPresent()) {
            return ResponseEntity.ok(auditoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
