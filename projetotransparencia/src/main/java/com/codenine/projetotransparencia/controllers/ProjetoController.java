// controllers/ProjetoController.java
package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.services.ProjetoService;
import com.codenine.projetotransparencia.utils.ConversorData;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projeto")
@CrossOrigin(origins = "http://localhost:3000")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarProjeto(
            @RequestParam String titulo,
            @RequestParam String referenciaProjeto,
            @RequestParam String empresa,
            @RequestParam String objeto,
            @RequestParam(required = false) String descricao,
            @RequestParam String nomeCoordenador,
            @RequestParam Double valor,
            @RequestParam String dataInicio,
            @RequestParam String dataTermino,
            @RequestPart(required = false) MultipartFile resumoPdf,
            @RequestPart(required = false) MultipartFile resumoExcel) {

        try {
            Date dataInicioDate = ConversorData.converterIsoParaData(dataInicio);
            Date dataTerminoDate = ConversorData.converterIsoParaData(dataTermino);

            var cadastrarProjetoDto = new CadastrarProjetoDto(
                    titulo,
                    referenciaProjeto,
                    empresa,
                    objeto,
                    Optional.ofNullable(descricao),
                    nomeCoordenador,
                    valor,
                    dataInicioDate,
                    dataTerminoDate,
                    Optional.ofNullable(resumoPdf != null ? resumoPdf.getBytes() : null),
                    Optional.ofNullable(resumoExcel != null ? resumoExcel.getBytes() : null)
            );

            var projetoId = projetoService.cadastrarProjeto(cadastrarProjetoDto);
            return ResponseEntity.created(URI.create("/projeto/visualizar/" + projetoId.toString())).body("Projeto cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datas inváliadas");
        }
    }

    @GetMapping("/listar")
    public List<Projeto> getAllProjetos() {
        return projetoService.listarProjetos();
    }

    @GetMapping("/visualizar/{id}")
    public Projeto getProjetoById(@PathVariable Long id) {
        return projetoService.visualizarProjeto(id);
    }

    /* @PutMapping("/atualizar/{id}")
    public Projeto updateProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        return projetoService.updateProjeto(id, projeto);
    } */
}