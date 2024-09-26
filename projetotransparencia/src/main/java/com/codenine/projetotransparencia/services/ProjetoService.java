// services/ProjetoService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.CadastrarProjetoDto;
import com.codenine.projetotransparencia.controllers.dto.BuscarProjetoDto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarProjetoDto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.utils.documents.VerificarExcel;
import com.codenine.projetotransparencia.utils.documents.VerificarPdf;
import com.codenine.projetotransparencia.utils.documents.VerificarTamanho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private VerificarExcel verificarExcel;

    @Autowired
    private VerificarPdf verificarPdf;

    @Autowired
    private VerificarTamanho verificarTamanho;

    @Autowired
    private ObjectMapper objectMapper;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Long cadastrarProjeto(CadastrarProjetoDto cadastrarProjetoDto) throws IOException {

        Projeto projeto = new Projeto(
                cadastrarProjetoDto.titulo(),
                cadastrarProjetoDto.referencia(),
                cadastrarProjetoDto.contratante().orElse(null),
                null, // objeto
                null, // descricao
                cadastrarProjetoDto.nomeCoordenador(),
                cadastrarProjetoDto.valor().orElse(null),
                cadastrarProjetoDto.dataInicio(),
                cadastrarProjetoDto.dataTermino().orElse(null),
                null, // resumoPdf
                null, // resumoExcel
                null, // proposta
                null  // contrato
        );

        return projetoRepository.save(projeto).getId();
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public List<Projeto> buscarProjetos(BuscarProjetoDto filtro) {

        String referencia = filtro.referencia();
        String nomeCoordenador = filtro.nomeCoordenador();
        String dataInicioStr = filtro.dataInicio();
        String dataTerminoStr = filtro.dataTermino();
        Double valor = filtro.valor();

        Date dataInicio = null;
        Date dataTermino = null;

        try {
            if (StringUtils.hasText(dataInicioStr)) {
                dataInicio = dateFormat.parse(dataInicioStr);
            }
            if (StringUtils.hasText(dataTerminoStr)) {
                dataTermino = dateFormat.parse(dataTerminoStr);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter datas", e);
        }

        if (StringUtils.hasText(referencia) ||
                StringUtils.hasText(nomeCoordenador) ||
                dataInicio != null ||
                dataTermino != null
        ) {
            // Filtra os projetos com base nos parâmetros fornecidos
            return projetoRepository.findByFiltros(referencia, nomeCoordenador, dataInicio, dataTermino, valor);
        } else {
            // Se nenhum filtro foi passado, retorna todos os projetos
            return projetoRepository.findAll();
        }
    }

    public Projeto visualizarProjeto(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }

    public Long atualizarProjeto(AtualizarProjetoDto atualizarProjetoDto) throws IOException {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(atualizarProjetoDto.id());

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + atualizarProjetoDto.id() + " não encontrado!");
        }

        Projeto projeto = projetoOpcional.get();
        if (atualizarProjetoDto.projeto() != null && !atualizarProjetoDto.projeto().isEmpty()) {
            Projeto projetoAtualizado;
            try {
                projetoAtualizado = objectMapper.readValue(atualizarProjetoDto.projeto(), Projeto.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao processar o JSON do projeto" + e.getMessage(), e);
            }

            if (projetoAtualizado.getTitulo() != null) {
                projeto.setTitulo(projetoAtualizado.getTitulo());
            }
            if (projetoAtualizado.getReferencia() != null) {
                projeto.setReferencia(projetoAtualizado.getReferencia());
            }
            if (projetoAtualizado.getContratante() != null) {
                projeto.setContratante(projetoAtualizado.getContratante());
            }
            if (projetoAtualizado.getObjeto() != null) {
                projeto.setObjeto(projetoAtualizado.getObjeto());
            }
            if (projetoAtualizado.getDescricao() != null) {
                projeto.setDescricao(projetoAtualizado.getDescricao());
            }
            if (projetoAtualizado.getNomeCoordenador() != null) {
                projeto.setNomeCoordenador(projetoAtualizado.getNomeCoordenador());
            }
            if (projetoAtualizado.getValor() != null) {
                projeto.setValor(projetoAtualizado.getValor());
            }
            if (projetoAtualizado.getDataInicio() != null) {
                projeto.setDataInicio(projetoAtualizado.getDataInicio());
            }
            if (projetoAtualizado.getDataTermino() != null) {
                projeto.setDataTermino(projetoAtualizado.getDataTermino());
            }
        }

//        BeanUtils.copyProperties(projetoAtualizado, projeto, getNullPropertyNames(projetoAtualizado));

        if (atualizarProjetoDto.resumoPdf().isPresent()) {
            projeto.getDocumentos().stream()
                    .filter(doc -> "resumoPdf".equals(doc.getTipo()))
                    .findFirst()
                    .ifPresent(doc -> documentoService.excluirDocumento(doc.getId()));
            documentoService.uploadDocumento(atualizarProjetoDto.resumoPdf().get(), projeto, "resumoPdf");
        }

        if (atualizarProjetoDto.resumoExcel().isPresent()) {
            projeto.getDocumentos().stream()
                    .filter(doc -> "resumoExcel".equals(doc.getTipo()))
                    .findFirst()
                    .ifPresent(doc -> documentoService.excluirDocumento(doc.getId()));
            documentoService.uploadDocumento(atualizarProjetoDto.resumoExcel().get(), projeto, "resumoExcel");
        }

        if (atualizarProjetoDto.proposta().isPresent()) {
            projeto.getDocumentos().stream()
                    .filter(doc -> "proposta".equals(doc.getTipo()))
                    .findFirst()
                    .ifPresent(doc -> documentoService.excluirDocumento(doc.getId()));
            documentoService.uploadDocumento(atualizarProjetoDto.proposta().get(), projeto, "proposta");
        }

        if (atualizarProjetoDto.contrato().isPresent()) {
            projeto.getDocumentos().stream()
                    .filter(doc -> "contrato".equals(doc.getTipo()))
                    .findFirst()
                    .ifPresent(doc -> documentoService.excluirDocumento(doc.getId()));
            documentoService.uploadDocumento(atualizarProjetoDto.contrato().get(), projeto, "contrato");
        }

        projetoRepository.save(projeto);
        return projeto.getId();
    }


    public void deletarProjeto(Long id) {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(id);

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + id + " não encontrado!");
        }

        projetoRepository.deleteById(id);
    }
}

//

//        return projetoRepository.save(projeto).getId();
//    }
