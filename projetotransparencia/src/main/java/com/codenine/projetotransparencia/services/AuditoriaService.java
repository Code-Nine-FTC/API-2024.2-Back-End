package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import com.codenine.projetotransparencia.repository.AuditoriaRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private AuditoriaRepositoryCustomImpl auditoriaRepositoryCustom;

    public List<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }

    // Método para buscar auditoria por ID
    public Optional<Auditoria> buscarAuditoriaPorId(Long id) {
        return auditoriaRepository.findById(id);
    }

    public List<Auditoria> buscarPorTipo(String tipoAuditoria) {
        return auditoriaRepository.findByTipoAuditoria(tipoAuditoria);
    }

    public List<Auditoria> buscarPorReferencia(String referenciaProjeto) {
        return auditoriaRepository.findByReferenciaProjeto(referenciaProjeto);
    }

    public List<Auditoria> buscarPorTipoEReferencia(String tipoAuditoria, String referenciaProjeto) {
        return auditoriaRepository.findByTipoAuditoriaAndReferenciaProjeto(tipoAuditoria, referenciaProjeto);
    }

    // Método para registrar auditoria
    public void registrarAuditoria(Projeto projetoAtual, Projeto projetoAntesDaAtualizacao) {
        Auditoria auditoria = new Auditoria();
        auditoria.setProjeto(projetoAtual);
        auditoria.setTipoAuditoria("Atualização");
        auditoria.setNomeCoordenador(projetoAtual.getNomeCoordenador());
        auditoria.setReferenciaProjeto(projetoAtual.getReferencia());

        // Captura todos os campos antigos
        auditoria.setTitulo_antigo(projetoAntesDaAtualizacao.getTitulo());
        auditoria.setContratante_antigo(projetoAntesDaAtualizacao.getContratante());
        auditoria.setDescricao_antiga(projetoAntesDaAtualizacao.getDescricao());
        auditoria.setValor_antigo(projetoAntesDaAtualizacao.getValor());
        auditoria.setDataInicio_antiga(projetoAntesDaAtualizacao.getDataInicio());
        auditoria.setDataTermino_antiga(projetoAntesDaAtualizacao.getDataTermino());
        auditoria.setStatus_antigo(projetoAntesDaAtualizacao.getStatus());
        auditoria.setIntegrantes_antigos(projetoAntesDaAtualizacao.getIntegrantes());
        //auditoria.setObjetivo_antigo(projetoAntesDaAtualizacao.getObjetivo());
        auditoria.setLinks_antigos(projetoAntesDaAtualizacao.getLinks());

        // Captura todos os campos novos
        auditoria.setTitulo_novo(projetoAtual.getTitulo());
        auditoria.setContratante_novo(projetoAtual.getContratante());
        auditoria.setDescricao_novo(projetoAtual.getDescricao());
        auditoria.setValor_novo(projetoAtual.getValor());
        auditoria.setDataInicio_novo(projetoAtual.getDataInicio());
        auditoria.setDataTermino_novo(projetoAtual.getDataTermino());
        auditoria.setStatus_novo(projetoAtual.getStatus());
        auditoria.setIntegrantes_novo(projetoAtual.getIntegrantes());
        //auditoria.setObjetivo_novo(projetoAtual.getObjetivo());
        auditoria.setLinks_novo(projetoAtual.getLinks());
        auditoria.setDataAlteracao(LocalDateTime.now());

        // Adicione verificações para os campos que não podem ser nulos
        if (auditoria.getDataInicio_antiga() == null || auditoria.getDataInicio_novo() == null) {
            throw new IllegalArgumentException("Os campos de data de início não podem ser nulos.");
        }

        auditoriaRepository.save(auditoria);
    }


    public List<Auditoria> buscarAuditoriasPorProjetoId(Long projetoId) {
        return auditoriaRepository.findByProjetoId(projetoId);
    }
}
