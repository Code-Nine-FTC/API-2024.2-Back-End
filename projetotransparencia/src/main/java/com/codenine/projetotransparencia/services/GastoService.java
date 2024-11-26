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
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    public Gasto buscarGastoPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gasto não encontrado para o ID: " + id));
    }

    public List<Gasto> listarGastos() {
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

    public void editarGasto(Long id, CadastrarGastoDto cadastrarGastoDto) throws IllegalArgumentException, IOException {
        Gasto gastoExistente = gastoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gasto não encontrado para o ID: " + id));

        Gasto gastoAtualizado;
        try {
            gastoAtualizado = objectMapper.readValue(cadastrarGastoDto.gasto(), Gasto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao processar o JSON do gasto: " + e.getMessage(), e);
        }

        // Atualiza os campos necessários
        gastoExistente.setDocumento(gastoAtualizado.getDocumento());
        gastoExistente.setTipoDocumento(gastoAtualizado.getTipoDocumento());
        gastoExistente.setFornecedor(gastoAtualizado.getFornecedor());
        gastoExistente.setData(gastoAtualizado.getData());
        gastoExistente.setValor(gastoAtualizado.getValor());
        gastoExistente.setMaterial(gastoAtualizado.getMaterial());

        Projeto projeto = projetoRepository.findById(cadastrarGastoDto.idProjeto())
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));
        gastoExistente.setProjeto(projeto);

        gastoRepository.save(gastoExistente);

        // Se houver nova nota fiscal, faz o upload
        if (cadastrarGastoDto.notaFiscal().isPresent()) {
            MultipartFile notaFiscal = cadastrarGastoDto.notaFiscal().get();
            documentoService.uploadDocumento(notaFiscal, gastoExistente, "notaFiscal");
        }
    }

    public void excluirGasto(Long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gasto não encontrado para o ID: " + id));
        gastoRepository.delete(gasto);
    }
}
