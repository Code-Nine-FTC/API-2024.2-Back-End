package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.CadastrarGastoDto;
import com.codenine.projetotransparencia.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/gasto")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarGasto(
            @RequestParam(value = "gasto") String gasto,
            @RequestParam(value = "idProjeto") Long projetoId,
            @RequestParam(required = false, value = "notaFiscal") MultipartFile notaFiscal) {

        try {
            CadastrarGastoDto cadastrarGastoDto = new CadastrarGastoDto(
                    gasto,
                    projetoId,
                    Optional.ofNullable(notaFiscal)
            );

            gastoService.cadastrarGasto(cadastrarGastoDto);

            return ResponseEntity.ok(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarGasto(@PathVariable Long id,
                                         @RequestParam(value = "gasto") String gasto,
                                         @RequestParam(value = "idProjeto") Long projetoId,
                                         @RequestParam(required = false, value = "notaFiscal") MultipartFile notaFiscal) {
        try {
            CadastrarGastoDto cadastrarGastoDto = new CadastrarGastoDto(
                    gasto,
                    projetoId,
                    Optional.ofNullable(notaFiscal)
            );

            gastoService.editarGasto(id, cadastrarGastoDto);

            return ResponseEntity.ok(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirGasto(@PathVariable Long id) {
        try {
            gastoService.excluirGasto(id);
            return ResponseEntity.ok("Gasto excluído com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
