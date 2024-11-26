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

    // Listar todas as classificações de demanda
    @GetMapping("/listar")
    public List<ClassificacaoDemanda> listarClassificacaoDemanda() {
        return classificacaoDemandaService.listarClassificacaoDemanda();
    }

    // Buscar uma classificação de demanda por ID
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<ClassificacaoDemanda> buscarClassificacaoDemandaPorId(@PathVariable Long id) {
        Optional<ClassificacaoDemanda> classificacaoDemanda = classificacaoDemandaService.buscarClassificacaoDemandaPorId(id);
        if (classificacaoDemanda.isPresent()) {
            return ResponseEntity.ok(classificacaoDemanda.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Cadastrar uma nova classificação de demanda
    @PutMapping("/cadastrar")
    public ResponseEntity<?> cadastrarClassificacaoDemanda(@RequestBody CadastrarClassificacaoDemandaDto classificacaoDemanda) {
        try {
            Long classificacaoDemandaId = classificacaoDemandaService.cadastrarClassificacaoDemanda(classificacaoDemanda);
            return ResponseEntity.created(null).body("Classificação de Demanda cadastrada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Editar uma classificação de demanda existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarClassificacaoDemanda(@PathVariable Long id, @RequestBody ClassificacaoDemanda novaClassificacao) {
        ClassificacaoDemanda classificacaoDemandaEditada = classificacaoDemandaService.editarClassificacaoDemanda(id, novaClassificacao);
        if (classificacaoDemandaEditada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Classificação de Demanda não encontrada para o ID: " + id);
        }
        return ResponseEntity.ok(classificacaoDemandaEditada);
    }

    // Excluir uma classificação de demanda pelo ID
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirClassificacaoDemanda(@PathVariable Long id) {
        boolean excluido = classificacaoDemandaService.excluirClassificacaoDemanda(id);
        if (!excluido) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Classificação de Demanda não encontrada para o ID: " + id);
        }
        return ResponseEntity.ok("Classificação de Demanda excluída com sucesso");
    }
}

