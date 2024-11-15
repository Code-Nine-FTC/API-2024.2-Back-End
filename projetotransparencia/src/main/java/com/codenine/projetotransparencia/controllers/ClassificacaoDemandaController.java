package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.services.ClassificacaoDemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classificacao-demanda")
public class ClassificacaoDemandaController {

    @Autowired
    private ClassificacaoDemandaService classificacaoDemandaService;

    @GetMapping
    public List<ClassificacaoDemanda> listarClassificacaoDemanda() {
        return classificacaoDemandaService.listarClassificacaoDemanda();
    }

   @GetMapping("/{id}")
    public ResponseEntity<ClassificacaoDemanda> buscarClassificacaoDemandaPorId(@PathVariable Long id) {
       Optional<ClassificacaoDemanda> classificacaoDemanda = classificacaoDemandaService.buscarClassificacaoDemandaPorId(id);
        if (classificacaoDemanda.isPresent()) {
            return ResponseEntity.ok(classificacaoDemanda.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
