// controllers/ProjetoController.java
package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.AtualizarProjetoDto;
import com.codenine.projetotransparencia.controllers.dto.BuscarProjetoDto;
import com.codenine.projetotransparencia.controllers.dto.CadastrarProjetoDto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProjeto(@RequestBody CadastrarProjetoDto projeto) {
        try {
            var projetoId = projetoService.cadastrarProjeto(projeto);
            return ResponseEntity.created(URI.create("/projeto/visualizar/" + projetoId.toString())).body("Projeto cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datas inválidas");
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProjeto(
            @RequestParam(required = false, value = "projeto") String projetojson,
            @PathVariable Long id,
            @RequestPart(required = false) MultipartFile resumoPdf,
            @RequestPart(required = false) MultipartFile resumoExcel,
            @RequestPart(required = false) MultipartFile proposta,
            @RequestPart(required = false) MultipartFile contrato) {

        try {
            AtualizarProjetoDto atualizarProjetoDto = new AtualizarProjetoDto (
                    id,
                    projetojson,
                    Optional.ofNullable(resumoPdf != null ? resumoPdf.getBytes() : null),
                    Optional.ofNullable(resumoExcel != null ? resumoExcel.getBytes() : null),
                    Optional.ofNullable(proposta != null ? proposta.getBytes() : null),
                    Optional.ofNullable(contrato != null ? contrato.getBytes() : null)
            );

            projetoService.atualizarProjeto(atualizarProjetoDto);

            return ResponseEntity.ok(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datas inválidas");
        }
//            @PathVariable Long id,
//            @RequestParam(required = false) String titulo,
//            @RequestParam(required = false) String referenciaProjeto,
//            @RequestParam(required = false) String contratante,
//            @RequestParam(required = false) String objeto,
//            @RequestParam(required = false) String descricao,
//            @RequestParam(required = false) String nomeCoordenador,
//            @RequestParam(required = false) Double valor,
//            @RequestParam(required = false) String dataInicio,
//            @RequestParam(required = false) String dataTermino,
//            @RequestPart(required = false) MultipartFile resumoPdf,
//            @RequestPart(required = false) MultipartFile resumoExcel) {
//
//        try {
//            Optional<Date> dataInicioDate = Optional.ofNullable(dataInicio != null ? ConversorData.converterIsoParaData(dataInicio) : null);
//            Optional<Date> dataTerminoDate = Optional.ofNullable(dataTermino != null ? ConversorData.converterIsoParaData(dataTermino) : null);
//
//            var atualizarProjetoDto = new AtualizarProjetoDto(
//                    id,  // O ID é passado aqui
//                    Optional.ofNullable(titulo),
//                    Optional.ofNullable(referenciaProjeto),
//                    Optional.ofNullable(contratante),
//                    Optional.ofNullable(objeto),
//                    Optional.ofNullable(descricao),
//                    Optional.ofNullable(nomeCoordenador),
//                    Optional.ofNullable(valor),
//                    dataInicioDate,
//                    dataTerminoDate,
//                    Optional.ofNullable(resumoPdf != null ? resumoPdf.getBytes() : null),
//                    Optional.ofNullable(resumoExcel != null ? resumoExcel.getBytes() : null)
//            );
//
//            Long projetoId = projetoService.atualizarProjeto(atualizarProjetoDto);
//            return ResponseEntity.ok("Projeto atualizado com sucesso! ID: " + projetoId);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar arquivos.");
//        }

    }

    // Deletar projeto pelo ID
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProjeto(@PathVariable Long id) {
        try {
            projetoService.deletarProjeto(id);
            return ResponseEntity.ok("Projeto com ID " + id + " foi deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public List<Projeto> listarProjetos(
            @RequestParam(required = false) String referenciaProjeto,
            @RequestParam(required = false) String nomeCoordenador,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataTermino,
            @RequestParam(required = false) Double valor
    ) {
        BuscarProjetoDto filtro = new BuscarProjetoDto(
                referenciaProjeto,
                nomeCoordenador,
                dataInicio,
                dataTermino,
                valor
        );
        return projetoService.buscarProjetos(filtro);
    }

    // Visualizar um projeto por ID
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Projeto> visualizarProjeto(@PathVariable Long id) {
        Projeto projeto = projetoService.visualizarProjeto(id);

        if (projeto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(projeto);
    }

}