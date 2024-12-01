package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.repository.ConvenioRepository;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codenine.projetotransparencia.controllers.dto.CadastrarConvenioDto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarConvenioDto;

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
                cadastrarConvenioDto.nome(),
                cadastrarConvenioDto.cnpj(),
                cadastrarConvenioDto.email(),
                cadastrarConvenioDto.telefone(),
                cadastrarConvenioDto.areaColaboracao(),
                cadastrarConvenioDto.historicoParceria()
        );

        // Salva o convênio
        Convenio convenioCadastrado = convenioRepository.save(convenio);

        // Criação do registro na auditoria
        Auditoria auditoria = new Auditoria();
        auditoria.setTipoAuditoria("CRIAÇÃO CONVÊNIO");
        auditoria.setNomeConvenio_novo(convenioCadastrado.getNome());
        auditoria.setCnpjConvenio_novo(convenioCadastrado.getCnpj());
        auditoria.setEmailConvenio_novo(convenioCadastrado.getEmail());
        auditoria.setTelefoneConvenio_novo(convenioCadastrado.getTelefone());
        auditoria.setAreaColaboracaoConvenio_novo(convenioCadastrado.getAreaColaboracao());
        auditoria.setHistoricoParceriaConvenio_novo(convenioCadastrado.getHistoricoParceria());
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
        String nomeAntigo = convenio.getNome();
        String cnpjAntigo = convenio.getCnpj();
        String emailAntigo = convenio.getEmail();
        String telefoneAntigo = convenio.getTelefone();
        String areaColaboracaoAntigo = convenio.getAreaColaboracao();
        String historicoAntigo = convenio.getHistoricoParceria();

        // Atualizando os campos do convenio
        if (atualizarConvenioDto.nome().isPresent()) {
            convenio.setNome(atualizarConvenioDto.nome().get());
        }
        if (atualizarConvenioDto.cnpj().isPresent()) {
            convenio.setCnpj(atualizarConvenioDto.cnpj().get());
        }
        if (atualizarConvenioDto.email().isPresent()) {
            convenio.setEmail(atualizarConvenioDto.email().get());
        }
        if (atualizarConvenioDto.telefone().isPresent()) {
            convenio.setTelefone(atualizarConvenioDto.telefone().get());
        }
        if (atualizarConvenioDto.areaColaboracao().isPresent()) {
            convenio.setAreaColaboracao(atualizarConvenioDto.areaColaboracao().get());
        }

        convenioRepository.save(convenio);

        // Criação do registro de auditoria
        Auditoria auditoria = new Auditoria();
        auditoria.setTipoAuditoria("ATUALIZAÇÃO CONVÊNIO");
        auditoria.setNomeConvenio_antigo(nomeAntigo);
        auditoria.setCnpjConvenio_antigo(cnpjAntigo);
        auditoria.setEmailConvenio_antigo(emailAntigo);
        auditoria.setTelefoneConvenio_antigo(telefoneAntigo);
        auditoria.setAreaColaboracaoConvenio_antigo(areaColaboracaoAntigo);
        auditoria.setHistoricoParceriaConvenio_antigo(historicoAntigo);

        auditoria.setNomeConvenio_novo(convenio.getNome());
        auditoria.setCnpjConvenio_novo(convenio.getCnpj());
        auditoria.setEmailConvenio_novo(convenio.getEmail());
        auditoria.setTelefoneConvenio_novo(convenio.getTelefone());
        auditoria.setAreaColaboracaoConvenio_novo(convenio.getAreaColaboracao());
        auditoria.setHistoricoParceriaConvenio_novo(convenio.getHistoricoParceria());
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
        auditoria.setNomeConvenio_antigo(convenio.getNome());
        auditoria.setCnpjConvenio_antigo(convenio.getCnpj());
        auditoria.setEmailConvenio_antigo(convenio.getEmail());
        auditoria.setTelefoneConvenio_antigo(convenio.getTelefone());
        auditoria.setAreaColaboracaoConvenio_antigo(convenio.getAreaColaboracao());
        auditoria.setHistoricoParceriaConvenio_antigo(convenio.getHistoricoParceria());
        auditoria.setDataAlteracao(LocalDateTime.now());

        // Salvando auditoria antes da exclusão
        auditoriaRepository.save(auditoria);

        // Deletando o convênio
        convenioRepository.deleteById(id);
    }
}
