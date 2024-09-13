// controllers/AdministradorController.java
package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Administrador;
import com.codenine.projetotransparencia.entities.Coordenador;
import com.codenine.projetotransparencia.services.AdministradorService;
import com.codenine.projetotransparencia.services.CoordenadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private CoordenadorService coordenadorService;

    private static final String EMAIL_ADMIN_FIXO = "administradorAfpg@gmail.com";

    @GetMapping
    public List<Administrador> getAllAdministradores() {
        return administradorService.getAllAdministradores();
    }

    @PostMapping("/coordenadores")
    public Coordenador createCoordenador(
            @RequestParam String adminEmail,
            @RequestBody Coordenador coordenador) {

        
        if (!adminEmail.equals(EMAIL_ADMIN_FIXO)) {
            throw new RuntimeException("Apenas o administrador fixo pode cadastrar coordenadores.");
        }

        // Cadastra o coordenador
        return coordenadorService.createCoordenador(coordenador);
    }
}
