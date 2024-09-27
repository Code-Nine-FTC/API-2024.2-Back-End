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
import com.codenine.projetotransparencia.utils.ConversorData;
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

    @Autowired ConversorData conversorData;

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
                    Optional.ofNullable(resumoPdf),
                    Optional.ofNullable(resumoExcel),
                    Optional.ofNullable(proposta),
                    Optional.ofNullable(contrato)
            );

            projetoService.atualizarProjeto(atualizarProjetoDto);

            return ResponseEntity.ok(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datas inválidas");
        }
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
        Date dateStart = conversorData.converterIsoParaData(dataInicio);
        Date dateEnd = conversorData.converterIsoParaData(dataTermino);

        BuscarProjetoDto filtro = new BuscarProjetoDto(
                referenciaProjeto,
                nomeCoordenador,
                dateStart,
                dateEnd,
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