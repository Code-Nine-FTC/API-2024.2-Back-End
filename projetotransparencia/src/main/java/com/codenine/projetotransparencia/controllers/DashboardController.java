package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        // Logging inicial da busca
        System.out.println("Iniciando busca de projetos com os seguintes parâmetros:");
        System.out.println("Coordenador: " + coordenador);
        System.out.println("Data Início: " + dataInicio);
        System.out.println("Data Término: " + dataTermino);
        System.out.println("Valor Máximo: " + valorMaximo);
        System.out.println("Valor Mínimo: " + valorMinimo);
        System.out.println("Situação do Projeto: " + situacaoProjeto);
        System.out.println("Tipo de Busca: " + tipoBusca);
        System.out.println("Contratante: " + contratante);

        try {
            Map<String, Long> resultados = dashboardService.contarProjetosDinamicos(
                    dataInicio, dataTermino, coordenador, valorMaximo, valorMinimo,
                    situacaoProjeto, tipoBusca, contratante
            );
            System.out.println("Resultados obtidos: " + resultados);
            return ResponseEntity.ok(resultados);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de argumento inválido: " + e.getMessage());
            Map<String, Long> errorResponse = new HashMap<>();
            errorResponse.put("error", 0L);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            Map<String, Long> errorResponse = new HashMap<>();
            errorResponse.put("error", 0L);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
