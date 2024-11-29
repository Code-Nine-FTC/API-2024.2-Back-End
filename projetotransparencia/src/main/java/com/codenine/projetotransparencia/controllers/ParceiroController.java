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
import com.codenine.projetotransparencia.controllers.dto.CadastrarParceiroDto;
import com.codenine.projetotransparencia.entities.Parceiro;
import com.codenine.projetotransparencia.services.ParceiroService;
import org.springframework.web.bind.annotation.PutMapping;
import com.codenine.projetotransparencia.controllers.dto.AtualizarParceiroDto;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/parceiro")
public class ParceiroController {

    @Autowired
    private ParceiroService parceiroService;


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarParceiro(@RequestBody CadastrarParceiroDto parceiro) {
        try {
            var parceiroId = parceiroService.cadastrarParceiro(parceiro);
            return ResponseEntity.created(URI.create("/parceiro/visualizar" + parceiroId.toString())).body("Parceiro cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public List<Parceiro> listarParceiros() {
        return parceiroService.listarParceiros();
    }

    @GetMapping("visualizar/{id}")
    public ResponseEntity<Parceiro> buscarParceiroPorId(@PathVariable Long id) {
        Optional<Parceiro> parceiro = parceiroService.buscarParceiroPorId(id);
        if (parceiro.isPresent()) {
            return ResponseEntity.ok(parceiro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<String> atualizarParceiro(@RequestBody AtualizarParceiroDto parceiro, @PathVariable Long id) {
        try {
            var parceiroId = parceiroService.atualizarParceiro(parceiro, id);
            return ResponseEntity.created(URI.create("/parceiro/visualizar" + parceiroId.toString())).body("Parceiro atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deleteParceiro(@PathVariable Long id) {
        try{
            parceiroService.deletarParceiro(id);
            return ResponseEntity.ok("Parceiro deletado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}