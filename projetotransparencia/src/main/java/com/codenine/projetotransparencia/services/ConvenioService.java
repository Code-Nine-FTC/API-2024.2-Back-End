package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.repository.ConvenioRepository;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codenine.projetotransparencia.controllers.dto.CadastrarConvenioDto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarConvenioDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    // Método para listar convenios
    public List<Convenio> listarConvenio() {
        return convenioRepository.findAll();
    }

    // Método para buscar convenio por id
    public Optional<Convenio> buscarConvenioPorId(Long id) {
        return convenioRepository.findById(id);
    }

    // Método para cadastrar um novo convenio
    public Long cadastrarConvenio(CadastrarConvenioDto cadastrarConvenioDto) {
        Convenio convenio = new Convenio(
                cadastrarConvenioDto.nomeInstituicao(),
                cadastrarConvenioDto.dataInicial(),
                cadastrarConvenioDto.dataFinal(),
                cadastrarConvenioDto.documentoClausulas()
        );

        // Salva o convênio
        Convenio convenioCadastrado = convenioRepository.save(convenio);

        // Criação do registro na auditoria
        Auditoria auditoria = new Auditoria();
        auditoria.setTipoAuditoria("CRIAÇÃO CONVÊNIO");
        auditoria.setNomeInstituicaoConvenio_novo(convenioCadastrado.getNomeInstituicao());
        auditoria.setDataInicialConvenio_novo(convenioCadastrado.getDataInicial());
        auditoria.setDataFinalConvenio_novo(convenioCadastrado.getDataFinal());
        auditoria.setDocumentoClausulasConvenio_novo(convenioCadastrado.getDocumentoClausulas());
        auditoria.setDataAlteracao(LocalDateTime.now());

        // Salvando auditoria
        auditoriaRepository.save(auditoria);

        return convenioCadastrado.getId();
    }

    // Método para atualizar um convenio
    public Long atualizarConvenio(AtualizarConvenioDto atualizarConvenioDto, Long id) throws IllegalArgumentException {
        Optional<Convenio> convenioOptional = convenioRepository.findById(id);
        if (convenioOptional.isEmpty()) {
            throw new IllegalArgumentException("Convenio não encontrado");
        }
        Convenio convenio = convenioOptional.get();

        // Armazenando os dados antigos para auditoria
        String nomeInstituicaoAntigo = convenio.getNomeInstituicao();
        LocalDate dataInicialAntigo = convenio.getDataInicial();
        LocalDate dataFinalAntigo = convenio.getDataFinal();
        String documentoClausulasAntigo = convenio.getDocumentoClausulas();

        // Atualizando os campos do convenio
        if (atualizarConvenioDto.nomeInstituicao().isPresent()) {
            convenio.setNomeInstituicao(atualizarConvenioDto.nomeInstituicao().get());
        }
        if (atualizarConvenioDto.dataInicial().isPresent()) {
            convenio.setDataInicial(atualizarConvenioDto.dataInicial().get());
        }
        if (atualizarConvenioDto.dataFinal().isPresent()) {
            convenio.setDataFinal(atualizarConvenioDto.dataFinal().get());
        }
        if (atualizarConvenioDto.documentoClausulas().isPresent()) {
            convenio.setDocumentoClausulas(atualizarConvenioDto.documentoClausulas().get());
        }

        convenioRepository.save(convenio);

        // Criação do registro de auditoria
        Auditoria auditoria = new Auditoria();
        auditoria.setTipoAuditoria("ATUALIZAÇÃO CONVÊNIO");
        auditoria.setNomeInstituicaoConvenio_antigo(nomeInstituicaoAntigo);
        auditoria.setDataInicialConvenio_antigo(dataInicialAntigo);
        auditoria.setDataFinalConvenio_antigo(dataFinalAntigo);
        auditoria.setDocumentoClausulasConvenio_antigo(documentoClausulasAntigo);


        auditoria.setNomeInstituicaoConvenio_novo(convenio.getNomeInstituicao());
        auditoria.setDataInicialConvenio_novo(convenio.getDataInicial());
        auditoria.setDataFinalConvenio_novo(convenio.getDataFinal());
        auditoria.setDocumentoClausulasConvenio_novo(convenio.getDocumentoClausulas());
        auditoria.setDataAlteracao(LocalDateTime.now());

        // Salvando auditoria
        auditoriaRepository.save(auditoria);

        return convenio.getId();
    }

    // Método para deletar um convenio
    public void deletarConvenio(Long id) {
        Optional<Convenio> convenioOptional = convenioRepository.findById(id);
        if (convenioOptional.isEmpty()) {
            throw new IllegalArgumentException("Convenio não encontrado");
        }
        Convenio convenio = convenioOptional.get();

        // Armazenando os dados do convênio antes de excluir para auditoria
        Auditoria auditoria = new Auditoria();
        auditoria.setTipoAuditoria("EXCLUSÃO CONVÊNIO");
        auditoria.setNomeInstituicaoConvenio_antigo(convenio.getNomeInstituicao());
        auditoria.setDataInicialConvenio_antigo(convenio.getDataInicial());
        auditoria.setDataFinalConvenio_antigo(convenio.getDataFinal());
        auditoria.setDocumentoClausulasConvenio_antigo(convenio.getDocumentoClausulas());
        auditoria.setDataAlteracao(LocalDateTime.now());

        // Salvando auditoria antes da exclusão
        auditoriaRepository.save(auditoria);

        // Deletando o convênio
        convenioRepository.deleteById(id);
    }
}
