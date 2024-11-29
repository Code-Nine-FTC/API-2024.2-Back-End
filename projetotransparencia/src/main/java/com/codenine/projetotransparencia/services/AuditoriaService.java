package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.AuditoriaRepository;
import com.codenine.projetotransparencia.repository.AuditoriaRepositoryCustomImpl;
import com.codenine.projetotransparencia.repository.DocumentoRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private AuditoriaRepositoryCustomImpl auditoriaRepositoryCustom;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

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
    public void registrarAuditoria(Long id, Projeto projetoAntesDaAtualizacao, String tipoAuditoria) {
        Projeto projetoAtual = projetoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
        Auditoria auditoria = new Auditoria();
        auditoria.setProjeto(projetoAtual);
        auditoria.setTipoAuditoria(tipoAuditoria);
        auditoria.setNomeCoordenador(projetoAtual.getNomeCoordenador());
        auditoria.setReferenciaProjeto(projetoAtual.getReferencia());

        // Captura todos os campos antigos
        auditoria.setTitulo_antigo(projetoAntesDaAtualizacao.getTitulo());
//        auditoria.setContratante_antigo(projetoAntesDaAtualizacao.getContratante());
        auditoria.setDescricao_antiga(projetoAntesDaAtualizacao.getDescricao());
        auditoria.setValor_antigo(projetoAntesDaAtualizacao.getValor());
        auditoria.setDataInicio_antiga(projetoAntesDaAtualizacao.getDataInicio());
        auditoria.setDataTermino_antiga(projetoAntesDaAtualizacao.getDataTermino());
        auditoria.setStatus_antigo(projetoAntesDaAtualizacao.getStatus());
        auditoria.setIntegrantes_antigos(projetoAntesDaAtualizacao.getIntegrantes());
        auditoria.setObjetivo_antigo(projetoAntesDaAtualizacao.getObjeto());
        auditoria.setLinks_antigos(projetoAntesDaAtualizacao.getLinks());
        auditoria.setCamposOcultos_antigo(projetoAntesDaAtualizacao.getCamposOcultos());

        // Captura todos os campos novos apenas se forem diferentes dos antigos
        if (!Objects.equals(projetoAtual.getTitulo(), projetoAntesDaAtualizacao.getTitulo())) {
            auditoria.setTitulo_novo(projetoAtual.getTitulo());
        }
//        if (!Objects.equals(projetoAtual.getContratante(), projetoAntesDaAtualizacao.getContratante())) {
//            auditoria.setContratante_novo(projetoAtual.getContratante());
//        }
        if (!Objects.equals(projetoAtual.getDescricao(), projetoAntesDaAtualizacao.getDescricao())) {
            auditoria.setDescricao_novo(projetoAtual.getDescricao());
        }
        if (!Objects.equals(projetoAtual.getValor(), projetoAntesDaAtualizacao.getValor())) {
            auditoria.setValor_novo(projetoAtual.getValor());
        }
        if (!Objects.equals(projetoAtual.getDataInicio(), projetoAntesDaAtualizacao.getDataInicio())) {
            auditoria.setDataInicio_novo(projetoAtual.getDataInicio());
        }
        if (!Objects.equals(projetoAtual.getDataTermino(), projetoAntesDaAtualizacao.getDataTermino())) {
            auditoria.setDataTermino_novo(projetoAtual.getDataTermino());
        }
        if (!Objects.equals(projetoAtual.getStatus(), projetoAntesDaAtualizacao.getStatus())) {
            auditoria.setStatus_novo(projetoAtual.getStatus());
        }
        if (!Objects.equals(projetoAtual.getIntegrantes(), projetoAntesDaAtualizacao.getIntegrantes())) {
            auditoria.setIntegrantes_novo(projetoAtual.getIntegrantes());
        }
        if (!Objects.equals(projetoAtual.getObjeto(), projetoAntesDaAtualizacao.getObjeto())) {
            auditoria.setObjetivo_novo(projetoAtual.getObjeto());
        }
        if (!Objects.equals(projetoAtual.getLinks(), projetoAntesDaAtualizacao.getLinks())) {
            auditoria.setLinks_novo(projetoAtual.getLinks());
        }
        if (!Objects.equals(projetoAtual.getCamposOcultos(), projetoAntesDaAtualizacao.getCamposOcultos())) {
            auditoria.setCamposOcultos_novo(projetoAtual.getCamposOcultos());
        }

        List<Documento> documentosAntigos = projetoAntesDaAtualizacao.getDocumentos();
        System.out.println("Documentos antigos: " + documentosAntigos);
        List<Documento> documentosNovos = new ArrayList<>(projetoAtual.getDocumentos());
        System.out.println("Documentos novos: " + documentosNovos);
        documentosNovos.removeAll(documentosAntigos);
        System.out.println("Documentos novos após remoção: " + documentosNovos);
        documentosNovos.forEach(documento -> {
                documento.setAuditoria(auditoria);
                System.out.println("Documento: " + documento);
                documentoRepository.save(documento);
        });

        auditoria.setDocumentos_novo(documentosNovos);

        auditoria.setDataAlteracao(LocalDateTime.now());

        auditoriaRepository.save(auditoria);
    }

    public void registrarExclusaoArquivo (Long projetoId, Long documentoId) {
        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
        Documento documento = documentoRepository.findById(documentoId).orElseThrow(() -> new IllegalArgumentException("Documento não encontrado."));;
        List<Documento> listaDocumentos = new ArrayList<>();
        listaDocumentos.add(documento);
        Auditoria auditoria = new Auditoria();
        auditoria.setProjeto(projeto);
        auditoria.setTipoAuditoria("Exclusão de arquivo");
        auditoria.setTitulo_antigo(projeto.getTitulo());
        auditoria.setNomeCoordenador(projeto.getNomeCoordenador());
        auditoria.setReferenciaProjeto(projeto.getReferencia());
        auditoria.setDataAlteracao(LocalDateTime.now());

        auditoriaRepository.save(auditoria);

        documento.setAuditoria(auditoria);
        documentoRepository.save(documento);

        auditoria.setDocumentos_novo(listaDocumentos);

        auditoriaRepository.save(auditoria);
    }

    public List<Auditoria> buscarAuditoriasPorProjetoId(Long projetoId) {
        return auditoriaRepository.findByProjetoId(projetoId);
    }
    public void registrarAuditoriaDeCadastro(Projeto projetoAntesDaAlteracao, Projeto projetoAtual) {
        // Verifique que os campos obrigatórios (não-nulos) sejam preenchidos corretamente
//        Auditoria auditoria = new Auditoria(
//                projetoAtual, // Relacionamento com o projeto
//                "Cadastro", // Tipo de auditoria
//                projetoAntesDaAlteracao.getNomeCoordenador(), // Nome do coordenador (obrigatório)
//                null, // Títulos antigos são nulos, pois é um novo projeto
//                null, // Contratante antigo
//                null, // Descrição antiga
//                null, // Valor antigo
//                null, // Data início antiga
//                null, // Data término antiga
//                null, // Status antigo
//                null, // Integrantes antigos
//                null, // Objetivo antigo
//                null, // Links antigos
//                projetoAntesDaAlteracao.getTitulo(), // Título novo diretamente do cadastro
//                projetoAntesDaAlteracao.getReferencia(), // Referência nova
//                projetoAntesDaAlteracao.getContratante(), // Contratante novo
//                projetoAntesDaAlteracao.getDescricao(), // Descrição nova
//                projetoAntesDaAlteracao.getValor(), // Valor novo
//                projetoAntesDaAlteracao.getDataInicio(), // Data início nova
//                projetoAntesDaAlteracao.getDataTermino(), // Data término nova
//                projetoAntesDaAlteracao.getStatus(), // Status novo
//                projetoAntesDaAlteracao.getIntegrantes(), // Integrantes novos
//                null, // Objetivo novo (não implementado no cadastro)
//                projetoAntesDaAlteracao.getLinks(), // Links novos
//                LocalDateTime.now() // Data da alteração
//        );
        Auditoria auditoria = new Auditoria();
        auditoria.setProjeto(projetoAtual);
        auditoria.setTipoAuditoria("Cadastro");
        auditoria.setNomeCoordenador(projetoAtual.getNomeCoordenador());
        auditoria.setReferenciaProjeto(projetoAtual.getReferencia());
        auditoria.setTitulo_novo(projetoAtual.getTitulo());
//        auditoria.setContratante_novo(projetoAtual.getContratante());
        auditoria.setDescricao_novo(projetoAtual.getDescricao());
        auditoria.setValor_novo(projetoAtual.getValor());
        auditoria.setDataInicio_novo(projetoAtual.getDataInicio());
        auditoria.setDataTermino_novo(projetoAtual.getDataTermino());
        auditoria.setStatus_novo(projetoAtual.getStatus());
        auditoria.setIntegrantes_novo(projetoAtual.getIntegrantes());
        auditoria.setLinks_novo(projetoAtual.getLinks());
        auditoria.setCamposOcultos_novo(projetoAtual.getCamposOcultos());
        auditoria.setDataAlteracao(LocalDateTime.now());

        // Salvar a auditoria no banco de dados
        auditoriaRepository.save(auditoria);
    }
    public void registrarAuditoriaDeAlteracao(Projeto projetoAntesDaAlteracao, Projeto projetoAtual, String tipoAuditoria) {
        // Criar um objeto de auditoria
        Auditoria auditoria = new Auditoria();

        auditoria.setProjeto(projetoAtual);
        auditoria.setTipoAuditoria(tipoAuditoria); // "Exclusão" ou algo similar
        auditoria.setNomeCoordenador(projetoAtual.getNomeCoordenador());
        auditoria.setReferenciaProjeto(projetoAtual.getReferencia());

        // Guardar os dados antigos (do projeto antes da alteração)
        auditoria.setTitulo_antigo(projetoAntesDaAlteracao.getTitulo());
//        auditoria.setContratante_antigo(projetoAntesDaAlteracao.getContratante());
        auditoria.setDescricao_antiga(projetoAntesDaAlteracao.getDescricao());
        auditoria.setValor_antigo(projetoAntesDaAlteracao.getValor());
        auditoria.setDataInicio_antiga(projetoAntesDaAlteracao.getDataInicio());
        auditoria.setDataTermino_antiga(projetoAntesDaAlteracao.getDataTermino());
        auditoria.setStatus_antigo(projetoAntesDaAlteracao.getStatus());
        auditoria.setIntegrantes_antigos(projetoAntesDaAlteracao.getIntegrantes());
        auditoria.setObjetivo_antigo(projetoAntesDaAlteracao.getObjeto());
        auditoria.setLinks_antigos(projetoAntesDaAlteracao.getLinks());
        auditoria.setCamposOcultos_antigo(projetoAntesDaAlteracao.getCamposOcultos());

        // Registrar a data de alteração
        auditoria.setDataAlteracao(LocalDateTime.now());

        auditoriaRepository.save(auditoria);

        projetoAntesDaAlteracao.getDocumentos().forEach(documento -> {
            documento.setAuditoria(auditoria);
            System.out.println("Documento: " + documento);
            documentoRepository.save(documento);
        });

        auditoria.setDocumentos_novo(projetoAntesDaAlteracao.getDocumentos());

        // Caso o projeto tenha sido marcado como inativo, registrar como exclusão
        if (!projetoAntesDaAlteracao.getAtivo() && projetoAtual.getAtivo() == false) {
            // Pode adicionar algo como 'exclusão' ou 'desativado' no campo de descrição
            auditoria.setTipoAuditoria("Exclusão");
        }

        // Salvar a auditoria no banco de dados
        auditoriaRepository.save(auditoria);
    }

}
