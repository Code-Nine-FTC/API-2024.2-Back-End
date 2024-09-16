// controllers/ProjetoController.java
package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private ProjetoService projetoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProjeto(@RequestBody Projeto projeto) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (projeto.getProjetoId() == null) {
            projetoRepository.save(projeto);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/listar")
    public List<Projeto> getAllProjetos() {
        return projetoService.listarProjetos();
    }

    @GetMapping("/visualizar/{id}")
    public Projeto getProjetoById(@PathVariable Long id) {
        return projetoService.visualizarProjeto(id);
    }

    /* @PutMapping("/atualizar/{id}")
    public Projeto updateProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.updateProjeto(id, projeto);
    } */
}