// controllers/ProjetoController.java
package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public Projeto createProjeto(@RequestBody Projeto projeto) {
        return projetoService.createProjeto(projeto);
    }

    @GetMapping
    public List<Projeto> getAllProjetos() {
        return projetoService.getAllProjetos();
    }

    @GetMapping("/{id}")
    public Projeto getProjetoById(@PathVariable Long id) {
        return projetoService.getProjetoById(id);
    }
}