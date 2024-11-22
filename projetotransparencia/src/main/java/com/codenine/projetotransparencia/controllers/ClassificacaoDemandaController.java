package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.CadastrarClassificacaoDemandaDto;
import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.services.ClassificacaoDemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classificacao-demanda")
public class ClassificacaoDemandaController {

    @Autowired
    private ClassificacaoDemandaService classificacaoDemandaService;

    @GetMapping("/listar")
    public List<ClassificacaoDemanda> listarClassificacaoDemanda() {
        return classificacaoDemandaService.listarClassificacaoDemanda();
    }

   @GetMapping("/visualizar/{id}")
    public ResponseEntity<ClassificacaoDemanda> buscarClassificacaoDemandaPorId(@PathVariable Long id) {
       Optional<ClassificacaoDemanda> classificacaoDemanda = classificacaoDemandaService.buscarClassificacaoDemandaPorId(id);
        if (classificacaoDemanda.isPresent()) {
            return ResponseEntity.ok(classificacaoDemanda.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cadastrar")
    public ResponseEntity<?> cadastrarClassificacaoDemanda(@RequestBody CadastrarClassificacaoDemandaDto classificacaoDemanda) {
        try {
            Long classificacaoDemandaId = classificacaoDemandaService.cadastrarClassificacaoDemanda(classificacaoDemanda);
            return ResponseEntity.created(null).body("Classificação de Demanda cadastrada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
