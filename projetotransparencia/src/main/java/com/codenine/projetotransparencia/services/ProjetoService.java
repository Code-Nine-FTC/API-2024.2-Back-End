
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.CadastrarProjetoDto;
import com.codenine.projetotransparencia.controllers.dto.BuscarProjetoDto;
import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarProjetoDto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.utils.documents.VerificarExcel;
import com.codenine.projetotransparencia.utils.documents.VerificarPdf;
import com.codenine.projetotransparencia.utils.documents.VerificarTamanho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Autowired
    private NormalizacaoService normalizacaoService; // Nova dependência
    @Autowired
    private AuditoriaService auditoriaService;

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
                cadastrarProjetoDto.status().orElse(null),
                cadastrarProjetoDto.integrantes().orElse(null),
                //cadastrarProjetoDto.objetivo().orElse(null),
                cadastrarProjetoDto.links().orElse(null),
                cadastrarProjetoDto.camposOcultos().orElse(null),
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
        Date dataInicio = filtro.dataInicio();
        Date dataTermino = filtro.dataTermino();
        Double valor = filtro.valor();
        String status = filtro.status();
        String keyword = filtro.keyword();

        if (StringUtils.hasText(keyword) ||
                StringUtils.hasText(referencia) ||
                StringUtils.hasText(nomeCoordenador) ||
                StringUtils.hasText(status) ||
                dataInicio != null ||
                dataTermino != null) {
            return projetoRepository.findByFiltros(referencia, nomeCoordenador, dataInicio, dataTermino, valor, status, keyword);
        } else {
            return projetoRepository.findAll();
        }
    }

    public Projeto visualizarProjeto(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long atualizarProjeto(AtualizarProjetoDto atualizarProjetoDto) throws IOException {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(atualizarProjetoDto.id());

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + atualizarProjetoDto.id() + " não encontrado!");
        }

        Projeto projeto = projetoOpcional.get();
        Projeto projetoAntesDaAtualizacao = new Projeto(projeto);

        if (atualizarProjetoDto.projeto() != null && !atualizarProjetoDto.projeto().isEmpty()) {
            Projeto projetoAtualizado;
            try {
                projetoAtualizado = objectMapper.readValue(atualizarProjetoDto.projeto(), Projeto.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao processar o JSON do projeto: " + e.getMessage(), e);
            }

            if (projetoAtualizado.getTitulo() != null) {
                projeto.setTitulo(projetoAtualizado.getTitulo());
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
            if (projetoAtualizado.getStatus() != null) {
                projeto.setStatus(projetoAtualizado.getStatus());
            }
            if (projetoAtualizado.getIntegrantes() != null) {
                projeto.setIntegrantes(projetoAtualizado.getIntegrantes());
            }
            if (projetoAtualizado.getLinks() != null) {
                projeto.setLinks(projetoAtualizado.getLinks());
            }
            if(projetoAtualizado.getCamposOcultos() != null) {
                if (projetoAtualizado.getCamposOcultos() == "nenhum"){
                    projeto.setCamposOcultos("");
                }
                else {
                    projeto.setCamposOcultos(projetoAtualizado.getCamposOcultos());
                }
            }
        }

        // Lógica para documentos
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

        auditoriaService.registrarAuditoria(projeto.getId(), projetoAntesDaAtualizacao, "Atualização");
        return projeto.getId();
    }


    public void deletarProjeto(Long id) {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(id);

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + id + " não encontrado!");
        }

        projetoRepository.deleteById(id);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            salvarProjetosDoJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void salvarProjetosDoJson() throws IOException, ParseException {
        // Diretório inicial do projeto (assumido como 'projetotransparencia' ou onde o JAR está executando)
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        Path caminhoArquivo = null;

        // Lógica para procurar `raspagem-dados/projects_data.json` de dentro para fora
        while (currentDir != null) {
            Path potentialPath = currentDir.resolve("raspagem-dados").resolve("projects_data.json");

            if (Files.exists(potentialPath)) {
                caminhoArquivo = potentialPath;
                break;
            }

            // Sobe um diretório na hierarquia
            currentDir = currentDir.getParent();
        }

        // Verifica se o caminho foi encontrado
        if (caminhoArquivo == null) {
            throw new FileNotFoundException("O arquivo 'projects_data.json' não foi encontrado em nenhum diretório pai do projeto.");
        }

        // Carrega o JSON dos projetos
        JsonNode projetosNode = objectMapper.readTree(caminhoArquivo.toFile());

        for (JsonNode projetoNode : projetosNode) {
            String titulo = projetoNode.has("Referência do projeto") ? projetoNode.get("Referência do projeto").asText() : "Título não fornecido";
            String referencia = titulo;
            String nomeCoordenador = projetoNode.has("Coordenador") ? projetoNode.get("Coordenador").asText() : "Coordenador não informado";

            String dataInicioString = projetoNode.has("Data de início") ? projetoNode.get("Data de início").asText() : "";
            String dataTerminoString = projetoNode.has("Data de término") ? projetoNode.get("Data de término").asText() : "";

            String status = "Concluído";
            String integrantes = null;
            String objetivo = null;
            String links = null;

            String dataInicioNormalizada = dataInicioString.isEmpty() ? "01/01/1900" : normalizacaoService.normalizarData(dataInicioString);
            String dataTerminoNormalizada = dataTerminoString.isEmpty() ? "01/01/1900" : normalizacaoService.normalizarData(dataTerminoString);

            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicioNormalizada);
            Date dataTermino = new SimpleDateFormat("dd/MM/yyyy").parse(dataTerminoNormalizada);

            Optional<Double> valor = Optional.ofNullable(projetoNode.get("Valor do projeto").asText())
                    .map(normalizacaoService::normalizarValorMonetario)
                    .filter(val -> !val.isEmpty())
                    .map(Double::parseDouble);

            Optional<String> contratante = Optional.of(projetoNode.has("Empresa") ? projetoNode.get("Empresa").asText() : "");

            Optional<String> camposOcultos = Optional.ofNullable(projetoNode.has("Campos ocultos") ? projetoNode.get("Campos ocultos").asText() : "");
            CadastrarProjetoDto dto = new CadastrarProjetoDto(
                    titulo,
                    referencia,
                    nomeCoordenador,
                    dataInicio,
                    valor,
                    Optional.ofNullable(dataTermino),
                    contratante,
                    Optional.ofNullable(status),
                    Optional.ofNullable(integrantes),
                    Optional.ofNullable(objetivo),
                    Optional.ofNullable(links),
                    camposOcultos
            );

            // Verifica se o projeto já existe
            List<Projeto> projetosExistentes = projetoRepository.findByReferencia(referencia);
            if (projetosExistentes.isEmpty()) {
                // Nenhum projeto encontrado, cria novo
                cadastrarProjeto(dto);
            } else {
                // Se o projeto existir, atualiza suas informações
                Projeto projetoExistente = projetosExistentes.get(0);
                atualizarProjeto(projetoExistente, dto);
            }
        }
    }

    // atualiza o projeto no bd
    private void atualizarProjeto(Projeto projetoExistente, CadastrarProjetoDto dto) {
        projetoExistente.setNomeCoordenador(dto.nomeCoordenador());
        projetoExistente.setDataInicio(dto.dataInicio());
        projetoExistente.setDataTermino(dto.dataTermino().orElse(null));
        projetoExistente.setValor(dto.valor().orElse(null));
        projetoExistente.setContratante(dto.contratante().orElse(null));

        projetoRepository.save(projetoExistente);
    }
}