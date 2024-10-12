package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/projetos/search")
    public ResponseEntity<?> getValues(
        @RequestParam(required = false) String coordenador,
        @RequestParam(required = false) String dataInicio,
        @RequestParam(required = false) String dataTermino,
        @RequestParam(required = false) String valorMaximo,
        @RequestParam(required = false) String valorMinimo,
        @RequestParam(required = false) String situacaoProjeto,
        @RequestParam(required = false) String tipoBusca,
        @RequestParam(required = false) String contratante) {

        
        System.out.println("Nome Coordenador: " + coordenador);
        System.out.println("Data Início: " + dataInicio);
        System.out.println("Data Término: " + dataTermino);
        System.out.println("Valor Máximo: " + valorMaximo);
        System.out.println("Valor Mínimo: " + valorMinimo);
        System.out.println("Situação Projeto: " + situacaoProjeto);
        System.out.println("Tipo Busca: " + tipoBusca);
        System.out.println("Contratante: " + contratante);

        
        return ResponseEntity.ok("Valores recebidos com sucesso");
    }
}
