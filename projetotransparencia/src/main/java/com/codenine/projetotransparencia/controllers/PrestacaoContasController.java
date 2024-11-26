package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.PrestacaoContas;
import com.codenine.projetotransparencia.services.PrestacaoContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prestacao-contas")
public class PrestacaoContasController {

    @Autowired
    private PrestacaoContasService prestacaoContasService;

    // Endpoint que retorna todas as prestações de contas
    @GetMapping("/listar")
    public List<PrestacaoContas> listarPrestacaoContas() {
        List<PrestacaoContas> prestacoesContas = prestacaoContasService.listarPrestacaoContas();
        return prestacoesContas;
    }

    // Novo endpoint que aceita um parâmetro de título de projeto
    @GetMapping("/listar-por-titulo")
    public List<PrestacaoContas> listarPrestacaoContasPorTituloProjeto(@RequestParam String titulo) {
        List<PrestacaoContas> prestacoesContas = prestacaoContasService.listarPrestacaoContasPorTituloProjeto(titulo);
        return prestacoesContas;
    }

    // Endpoint que retorna uma prestação de contas por ID
    @GetMapping("/visualizar/{id}")
    public PrestacaoContas visualizarPrestacaoContas(Long id) {
        PrestacaoContas prestacaoContas = prestacaoContasService.visualizarPrestacaoContas(id);
        return prestacaoContas;
    }
}
