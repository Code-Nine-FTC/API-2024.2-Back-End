package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.AtualizarConvenioDto;
import com.codenine.projetotransparencia.controllers.dto.CadastrarConvenioDto;
import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.services.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Convenio> buscarConvenioPorId(@PathVariable Long id) {
        return convenioService.buscarConvenioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarConvenio(@RequestBody CadastrarConvenioDto convenio) {
        try {
            Long convenioId = convenioService.cadastrarConvenio(convenio);
            URI location = URI.create("/convenio/visualizar/" + convenioId);
            return ResponseEntity.created(location).body("Convênio cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarConvenio(@RequestBody AtualizarConvenioDto convenio, @PathVariable Long id) {
        try {
            Long convenioId = convenioService.atualizarConvenio(convenio, id);
            URI location = URI.create("/convenio/visualizar/" + convenioId);
            return ResponseEntity.created(location).body("Convênio atualizado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteConvenio(@PathVariable Long id) {
        try {
            convenioService.deletarConvenio(id);
            return ResponseEntity.ok("Convênio deletado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
