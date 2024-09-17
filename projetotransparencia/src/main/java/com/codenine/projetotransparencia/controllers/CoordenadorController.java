/*
// controllers/CoordenadorController.java
package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Coordenador;
import com.codenine.projetotransparencia.services.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordenadores")
public class CoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @PostMapping
    public Coordenador createCoordenador(@RequestBody Coordenador coordenador) {
        return coordenadorService.createCoordenador(coordenador);
    }

    @GetMapping
    public List<Coordenador> getAllCoordenadores() {
        return coordenadorService.getAllCoordenadores();
    }

    @GetMapping("/{id}")
    public Coordenador getCoordenadorById(@PathVariable Long id) {
        return coordenadorService.getCoordenadorById(id);
    }
}
*/
