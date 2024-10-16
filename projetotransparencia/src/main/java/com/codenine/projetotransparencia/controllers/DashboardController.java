package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/projetos/search")
    public ResponseEntity<Map<String, Long>> getValues(
        @RequestParam(required = false) String coordenador,
        @RequestParam(required = false) String dataInicio,
        @RequestParam(required = false) String dataTermino,
        @RequestParam(required = false) String valorMaximo,
        @RequestParam(required = false) String valorMinimo,
        @RequestParam(required = false) String situacaoProjeto,
        @RequestParam(required = false) String tipoBusca,
        @RequestParam(required = false) String contratante) {

        // Chamando o método do serviço para contar os projetos dinamicamente
        Map<String, Long> resultado = dashboardService.contarProjetosDinamicos(coordenador, dataInicio, dataTermino,
                                                                             valorMaximo, valorMinimo,
                                                                             situacaoProjeto, tipoBusca, contratante);

        // Retornando a contagem dos projetos
        return ResponseEntity.ok(resultado);
    }
}

