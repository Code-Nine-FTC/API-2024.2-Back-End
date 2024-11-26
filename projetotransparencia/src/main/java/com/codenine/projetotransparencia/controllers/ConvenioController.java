package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.services.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/convenio")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    @GetMapping("/listar")
    public List<Convenio> listarConvenio() {
        return convenioService.listarConvenio();
    }

    @GetMapping("/visualizar/{id}")
    public Convenio buscarConvenioPorId(Long id) {
        return convenioService.buscarConvenioPorId(id).get();
    }
}
