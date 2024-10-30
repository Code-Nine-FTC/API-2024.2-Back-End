package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.services.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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


    @GetMapping("/auditoria/search")
    public ResponseEntity<List<Auditoria>> buscarPorTipoEReferencia(
            @RequestParam(required = false) String tipoAuditoria,
            @RequestParam(required = false) String referenciaProjeto) {

        List<Auditoria> auditorias;

        if (tipoAuditoria != null && referenciaProjeto != null) {
            auditorias = auditoriaService.buscarPorTipoEReferencia(tipoAuditoria, referenciaProjeto);
        } else if (tipoAuditoria != null) {
            auditorias = auditoriaService.buscarPorTipo(tipoAuditoria);
        } else if (referenciaProjeto != null) {
            auditorias = auditoriaService.buscarPorReferencia(referenciaProjeto);
        } else {
            auditorias = auditoriaService.listarAuditorias(); // Retorna todas as auditorias se nenhum parâmetro for passado
        }

        if (auditorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(auditorias);
    }
}
