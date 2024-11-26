package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.CadastrarGastoDto;
import com.codenine.projetotransparencia.entities.Gasto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.GastoRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    public Gasto buscarGastoPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gasto não encontrado para o ID: " + id));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private DocumentoService documentoService;

    public List<Gasto> listarGastos(){
        return gastoRepository.findAll();
    }


    public List<Gasto> listarGastosPorAno(int ano) {
        return gastoRepository.findAll().stream()
                .filter(gasto -> {
                    LocalDate localDate = gasto.getData();
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    return cal.get(Calendar.YEAR) == ano;
                })
                .collect(Collectors.toList());
    }

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