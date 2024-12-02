package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.repository.ConvenioRepository;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codenine.projetotransparencia.controllers.dto.CadastrarConvenioDto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarConvenioDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DocumentoService documentoService;

    // Método para listar convenios
    public List<Convenio> listarConvenio() {
        return convenioRepository.findAll();
    }

    // Método para buscar convenio por ID
    public Optional<Convenio> buscarConvenioPorId(Long id) {
        return convenioRepository.findById(id);
    }

    // Método para cadastrar um novo convenio
    public Long cadastrarConvenio(CadastrarConvenioDto cadastrarConvenioDto) throws IllegalArgumentException, IOException {
        Convenio convenio;

        try {
            convenio = objectMapper.readValue(cadastrarConvenioDto.convenio(), Convenio.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao processar o JSON do convênio: " + e.getMessage(), e);
        }

        convenioRepository.save(convenio);

        if (cadastrarConvenioDto.arquivoDocumento().isPresent()) {
            MultipartFile documentoConvenio = cadastrarConvenioDto.arquivoDocumento().get();
            documentoService.uploadDocumento(documentoConvenio, convenio, "documentoClausulas");
        }

        Convenio convenioSalvo = convenioRepository.save(convenio);

        // Atualizando campos a partir do DTO
//        cadastrarConvenioDto.documentoClausulas().ifPresent(convenio::setDocumentoClausulas);

//        // Salva o convênio
//        Convenio convenioCadastrado = convenioRepository.save(convenio);
//
//        // Criação do registro de auditoria
//        Auditoria auditoria = new Auditoria();
//        auditoria.setTipoAuditoria("CRIAÇÃO CONVÊNIO");
//        auditoria.setDocumentoClausulasConvenio_novo(convenioCadastrado.getDocumentoClausulas());
//        auditoria.setDataAlteracao(LocalDateTime.now());
//
//        // Salvando auditoria
//        auditoriaRepository.save(auditoria);

        return convenioSalvo.getId();
    }

    // Método para atualizar um convenio
    public Long atualizarConvenio(AtualizarConvenioDto atualizarConvenioDto, Long id) throws IllegalArgumentException, IOException {
        Optional<Convenio> convenioOptional = convenioRepository.findById(id);

        if (convenioOptional.isEmpty()) {
            throw new IllegalArgumentException("Convenio não encontrado");
        }
        Convenio convenio = convenioOptional.get();
        Convenio convenioAntesDaAtualizacao = new Convenio(convenio);

        if (atualizarConvenioDto.documentoClausulas().isPresent()) {
            Convenio convenioAtualizado;
            try {
                convenioAtualizado = objectMapper.readValue(atualizarConvenioDto.documentoClausulas().get(), Convenio.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao processar o JSON do convênio: " + e.getMessage(), e);
            }

            if (convenioAtualizado.getNomeInstituicao() != null) {
                convenio.setNomeInstituicao(convenioAtualizado.getNomeInstituicao());
            }
            if (convenioAtualizado.getDataInicial() != null) {
                convenio.setDataInicial(convenioAtualizado.getDataInicial());
            }
            if (convenioAtualizado.getDataFinal() != null) {
                convenio.setDataFinal(convenioAtualizado.getDataFinal());
            }

            convenioRepository.save(convenio);

            if (atualizarConvenioDto.arquivoDocumento().isPresent()) {
                MultipartFile documentoConvenio = atualizarConvenioDto.arquivoDocumento().get();
                System.out.println("Documento convenio: " + documentoConvenio);
                documentoService.uploadDocumento(documentoConvenio, convenio, "documentoClausulas");
            }

            convenioRepository.save(convenio);



//         Armazenando os dados antigos para auditoria
//        String documentoClausulasAntigo = convenio.getDocumentoClausulas();

//        // Atualizando os campos do convenio
//        atualizarConvenioDto.documentoClausulas().ifPresent(convenio::setDocumentoClausulas);

//        convenioRepository.save(convenio);
//
//        // Criação do registro de auditoria
//        Auditoria auditoria = new Auditoria();
//        auditoria.setTipoAuditoria("ATUALIZAÇÃO CONVÊNIO");
//        auditoria.setDocumentoClausulasConvenio_antigo(documentoClausulasAntigo);
//        auditoria.setDocumentoClausulasConvenio_novo(convenio.getDocumentoClausulas());
//        auditoria.setDataAlteracao(LocalDateTime.now());
//
//        // Salvando auditoria
//        auditoriaRepository.save(auditoria);

//        return convenio.getId();
        }
        return convenio.getId();
    }

    // Método para deletar um convenio
    public void deletarConvenio(Long id) {
        Optional<Convenio> convenioOptional = convenioRepository.findById(id);
        if (convenioOptional.isEmpty()) {
            throw new IllegalArgumentException("Convenio não encontrado");
        }
        Convenio convenio = convenioOptional.get();

//        // Armazenando os dados do convênio antes de excluir para auditoria
//        Auditoria auditoria = new Auditoria();
//        auditoria.setTipoAuditoria("EXCLUSÃO CONVÊNIO");
//        auditoria.setDocumentoClausulasConvenio_antigo(convenio.getDocumentoClausulas());
//        auditoria.setDataAlteracao(LocalDateTime.now());
//
//        // Salvando auditoria antes da exclusão
//        auditoriaRepository.save(auditoria);

        // Deletando o convênio
        convenioRepository.deleteById(id);
    }
}
