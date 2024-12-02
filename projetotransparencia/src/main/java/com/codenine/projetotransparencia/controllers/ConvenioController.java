package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.AtualizarConvenioDto;
import com.codenine.projetotransparencia.controllers.dto.CadastrarConvenioDto;
import com.codenine.projetotransparencia.services.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/convenio")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    @GetMapping("/listar")
    public ResponseEntity<?> listarConvenios() {
        return ResponseEntity.ok(convenioService.listarConvenio());
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<?> visualizarConvenio(@PathVariable Long id) {
        return ResponseEntity.ok(convenioService.buscarConvenioPorId(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarConvenio(
            @RequestParam(value = "convenio") String convenio,
            @RequestParam(required = false, value = "idProjeto") Long projetoId,
            @RequestParam(required = false, value = "documentoClausulas") MultipartFile documentoClausulas) {

        try {
            // Ajuste para Optional
            CadastrarConvenioDto cadastrarConvenioDto = new CadastrarConvenioDto(
                    convenio,  // Passando como Optional
                    Optional.ofNullable(documentoClausulas)  // Passando como Optional
            );

            // Chamada do serviço com o DTO
            System.out.println("testando:" + cadastrarConvenioDto);
            Long convenioId = convenioService.cadastrarConvenio(cadastrarConvenioDto);
            URI location = URI.create("/convenio/visualizar/" + convenioId);

            return ResponseEntity.created(location).body("Convênio cadastrado com sucesso");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarConvenio(@PathVariable Long id,
                                               @RequestParam(required = false, value = "convenio") String convenio,
                                               @RequestParam(required = false, value = "documentoClausulas") MultipartFile documentoClausulas) {

        try {
            // Ajuste para Optional
            AtualizarConvenioDto atualizarConvenioDto = new AtualizarConvenioDto(
                    Optional.ofNullable(convenio),  // Passando como Optional
                    Optional.ofNullable(documentoClausulas)  // Passando como Optional
            );

            // Chamada do serviço com o DTO
            Long convenioId = convenioService.atualizarConvenio(atualizarConvenioDto, id);
            URI location = URI.create("/convenio/visualizar/" + convenioId);

            return ResponseEntity.created(location).body("Convênio atualizado com sucesso");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarConvenio(@PathVariable Long id) {
        try {
            convenioService.deletarConvenio(id);
            return ResponseEntity.ok("Convênio deletado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
