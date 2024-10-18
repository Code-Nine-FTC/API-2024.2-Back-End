package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard/projetos/search")
    public ResponseEntity<Map<String, Long>> buscarProjetos(
            @RequestParam(required = false) String coordenador,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataTermino,
            @RequestParam(required = false) String valorMaximo,
            @RequestParam(required = false) String valorMinimo,
            @RequestParam(required = false) String situacaoProjeto,
            @RequestParam(required = false) String tipoBusca,
            @RequestParam(required = false) String contratante

    ) {
        Map<String, Long> resultados = dashboardService.contarProjetosDinamicos(
                dataInicio, dataTermino, coordenador, valorMaximo, valorMinimo,
                situacaoProjeto, tipoBusca, contratante
        );

        return ResponseEntity.ok(resultados);
    }
}
