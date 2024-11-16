package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.CadastrarGastoDto;
import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.entities.Gasto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.GastoRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private DocumentoService documentoService;

    public void cadastrarGasto(CadastrarGastoDto cadastrarGastoDto) throws IllegalArgumentException, IOException {
        Gasto gasto;
        try {
            gasto = objectMapper.readValue(cadastrarGastoDto.gasto(), Gasto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao processar o JSON do gasto: " + e.getMessage(), e);
        }

        Projeto projeto = projetoRepository.findById(cadastrarGastoDto.idProjeto())
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));

        gasto.setProjeto(projeto);

        gastoRepository.save(gasto);

        if (cadastrarGastoDto.notaFiscal().isPresent()) {
            MultipartFile notaFiscal = cadastrarGastoDto.notaFiscal().get();
            documentoService.uploadDocumento(notaFiscal, gasto, "notaFiscal");
        }

        gastoRepository.save(gasto);
    }
}
