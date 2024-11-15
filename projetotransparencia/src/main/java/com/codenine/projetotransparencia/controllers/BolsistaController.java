package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.services.BolsistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bolsista")
public class BolsistaController {

    @Autowired
    private BolsistaService bolsistaService;

    @GetMapping
    public List<Bolsista> listarBolsista() {
        return bolsistaService.listarBolsista();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bolsista> buscarBolsistaPorId(@PathVariable Long id) {
        Optional<Bolsista> bolsista = bolsistaService.buscarBolsistaPorId(id);
        if (bolsista.isPresent()) {
            return ResponseEntity.ok(bolsista.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
