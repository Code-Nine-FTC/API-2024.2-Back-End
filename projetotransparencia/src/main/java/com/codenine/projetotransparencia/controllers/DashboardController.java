package com.codenine.projetotransparencia.controllers;


import com.codenine.projetotransparencia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;



@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/contarProjetosPorCoordenador")
    public ResponseEntity<Map<String, Long>> contarProjetos(@RequestParam String nomeCoordenador) {
        return ResponseEntity.ok(dashboardService.contarProjetosPorCoordenador(nomeCoordenador));
    }

}