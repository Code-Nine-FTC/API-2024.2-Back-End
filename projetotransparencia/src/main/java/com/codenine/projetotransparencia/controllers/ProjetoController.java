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
import java.util.Collections;
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datas inválidas");
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProjeto(
            @PathVariable Long id,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String referenciaProjeto,
            @RequestParam(required = false) String empresa,
            @RequestParam(required = false) String objeto,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String nomeCoordenador,
            @RequestParam(required = false) Double valor,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataTermino,
            @RequestPart(required = false) MultipartFile resumoPdf,
            @RequestPart(required = false) MultipartFile resumoExcel) {

        try {
            Optional<Date> dataInicioDate = Optional.ofNullable(dataInicio != null ? ConversorData.converterIsoParaData(dataInicio) : null);
            Optional<Date> dataTerminoDate = Optional.ofNullable(dataTermino != null ? ConversorData.converterIsoParaData(dataTermino) : null);

            var atualizarProjetoDto = new AtualizarProjetoDto(
                    id,  // O ID é passado aqui
                    Optional.ofNullable(titulo),
                    Optional.ofNullable(referenciaProjeto),
                    Optional.ofNullable(empresa),
                    Optional.ofNullable(objeto),
                    Optional.ofNullable(descricao),
                    Optional.ofNullable(nomeCoordenador),
                    Optional.ofNullable(valor),
                    dataInicioDate,
                    dataTerminoDate,
                    Optional.ofNullable(resumoPdf != null ? resumoPdf.getBytes() : null),
                    Optional.ofNullable(resumoExcel != null ? resumoExcel.getBytes() : null)
            );

            Long projetoId = projetoService.atualizarProjeto(atualizarProjetoDto);
            return ResponseEntity.ok("Projeto atualizado com sucesso! ID: " + projetoId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar arquivos.");
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

    // Listar todos os projetos
    @GetMapping("/listar")
    public ResponseEntity<List<Projeto>> listarProjetos() {
        List<Projeto> projetos = projetoService.listarProjetos();

        if (projetos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(projetos);
    }

    @GetMapping("/buscar")
    public List<Projeto> listarProjetos(
            @RequestParam(required = false) String referenciaProjeto,
            @RequestParam(required = false) String nomeCoordenador,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataTermino
    ) {
        BuscarProjetoDto filtro = new BuscarProjetoDto(
                referenciaProjeto,
                nomeCoordenador,
                dataInicio,
                dataTermino
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