package com.codenine.projetotransparencia.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codenine.projetotransparencia.controllers.dto.CadastrarBolsistaDto;
import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.services.BolsistaService;
import org.springframework.web.bind.annotation.PutMapping;
import com.codenine.projetotransparencia.controllers.dto.AtualizarBolsistaDto;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/bolsista")
public class BolsistaController {

    @Autowired
    private BolsistaService bolsistaService;


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarBolsista(@RequestBody CadastrarBolsistaDto bolsista) {
        try {
            var bolsistaId = bolsistaService.cadastrarBolsista(bolsista);
            return ResponseEntity.created(URI.create("/bolsista/visualizar" + bolsistaId.toString())).body("Bolsista cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } 
    }

    @GetMapping
    public List<Bolsista> listarBolsista() {
        return bolsistaService.listarBolsista();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bolsista> buscarBolsistaPorId(@PathVariable Long id) {
        Optional<Bolsista> bolsista = bolsistaService.buscarBolsistaPorId(id);
        if (bolsista.isPresent()) {
            return ResponseEntity.ok(bolsista.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("atualizar/{id}")
    public ResponseEntity<String> atualizarBolsista(@RequestBody AtualizarBolsistaDto bolsista, @PathVariable Long id) {
        try {
            var bolsistaId = bolsistaService.atualizarBolsista(bolsista, id);
            return ResponseEntity.created(URI.create("/bolsista/visualizar" + bolsistaId.toString())).body("Bolsista atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarBolsista(@PathVariable Long id) {
        try {
            bolsistaService.deletarBolsista(id);
            return ResponseEntity.ok("Bolsista deletado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
