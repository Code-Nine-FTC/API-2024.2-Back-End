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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    // Cadastro de um novo projeto
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
                cadastrarProjetoDto.objetivo().orElse(null),
                cadastrarProjetoDto.links().orElse(null),
                cadastrarProjetoDto.camposOcultos().orElse(null),
                null,
                null,
                null,
                null// Define os campos ocultos
        );
        return projetoRepository.save(projeto).getId();
    }

    // Listagem de todos os projetos, aplicando ocultação de campos
    public List<Projeto> listarProjetos() {
        List<Projeto> projetos = projetoRepository.findAll();
        projetos.forEach(this::removerCamposOcultos);
        return projetos;
    }

    // Busca de projetos com base em filtros, aplicando ocultação de campos
    public List<Projeto> buscarProjetos(BuscarProjetoDto filtro) {
        String referencia = filtro.referencia();
        String nomeCoordenador = filtro.nomeCoordenador();
        Date dataInicio = filtro.dataInicio();
        Date dataTermino = filtro.dataTermino();
        Double valor = filtro.valor();
        String status = filtro.status();

        List<Projeto> projetos = StringUtils.hasText(referencia) ||
                StringUtils.hasText(nomeCoordenador) ||
                StringUtils.hasText(status) ||
                dataInicio != null ||
                dataTermino != null
                ? projetoRepository.findByFiltros(referencia, nomeCoordenador, dataInicio, dataTermino, valor, status)
                : projetoRepository.findAll();

        projetos.forEach(this::removerCamposOcultos);
        return projetos;
    }

    // Visualização de um projeto específico, aplicando ocultação de campos
    public Projeto visualizarProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto != null) {
            removerCamposOcultos(projeto);
        }
        return projeto;
    }

    // Atualização de projeto sem ocultação, permitindo edição completa
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
                throw new IllegalArgumentException("Erro ao processar o JSON do projeto: " + e.getMessage(), e);
            }
            atualizarCamposProjeto(projeto, projetoAtualizado);
        }

        // Atualiza campos ocultos, se presente no DTO
        if (atualizarProjetoDto.camposOcultos().isPresent()) {
            projeto.setCamposOcultos(atualizarCamposOcultos(projeto.getCamposOcultos(), atualizarProjetoDto.camposOcultos().get()));
        }

        projetoRepository.save(projeto);
        return projeto.getId();
    }

    // Exclusão de um projeto
    public void deletarProjeto(Long id) {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(id);
        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + id + " não encontrado!");
        }
        projetoRepository.deleteById(id);
    }

    // Inicialização de projetos a partir de um JSON, aplicando atualização
    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            salvarProjetosDoJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Carregamento de projetos do JSON e atualização, incluindo campos ocultos
    public void salvarProjetosDoJson() throws IOException, ParseException {
        String workingDir = System.getProperty("user.dir");
        String caminho = workingDir + File.separator + "raspagem-dados/projects_data.json";
        JsonNode projetosNode = objectMapper.readTree(new File(caminho));

        for (JsonNode projetoNode : projetosNode) {
            String titulo = projetoNode.has("Referência do projeto") ? projetoNode.get("Referência do projeto").asText() : "Título não fornecido";
            String referencia = titulo;
            String nomeCoordenador = projetoNode.has("Coordenador") ? projetoNode.get("Coordenador").asText() : "Coordenador não informado";
            String dataInicioString = projetoNode.has("Data de início") ? projetoNode.get("Data de início").asText() : "";
            String dataTerminoString = projetoNode.has("Data de término") ? projetoNode.get("Data de término").asText() : "";
            String dataInicioNormalizada = dataInicioString.isEmpty() ? "01/01/1900" : normalizacaoService.normalizarData(dataInicioString);
            String dataTerminoNormalizada = dataTerminoString.isEmpty() ? "01/01/1900" : normalizacaoService.normalizarData(dataTerminoString);
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicioNormalizada);
            Date dataTermino = new SimpleDateFormat("dd/MM/yyyy").parse(dataTerminoNormalizada);

            Optional<Double> valor = Optional.ofNullable(projetoNode.get("Valor do projeto").asText())
                    .map(v -> v.replace(",", ".")
                            .replaceAll("\\.(?=.*\\.)", "")
                            .replaceAll("[^0-9.-]", ""))
                    .filter(val -> !val.isEmpty())
                    .map(Double::parseDouble);

            Optional<String> contratante = Optional.of(projetoNode.has("Empresa") ? projetoNode.get("Empresa").asText() : "");

            CadastrarProjetoDto dto = new CadastrarProjetoDto(
                    titulo,
                    referencia,
                    nomeCoordenador,
                    dataInicio,
                    valor,
                    Optional.ofNullable(dataTermino),
                    contratante,
                    Optional.of("Concluído"),
                    Optional.ofNullable(null),
                    Optional.ofNullable(null),
                    Optional.ofNullable(null),
                    Optional.ofNullable(null)  // Nenhum campo oculto inicial
            );

            List<Projeto> projetosExistentes = projetoRepository.findByReferencia(referencia);
            if (projetosExistentes.isEmpty()) {
                cadastrarProjeto(dto);
            } else {
                atualizarProjeto(projetosExistentes.get(0), dto);
            }
        }
    }

    // Atualiza o projeto com campos ocultos, se aplicável
    private void atualizarProjeto(Projeto projetoExistente, CadastrarProjetoDto dto) {
        projetoExistente.setNomeCoordenador(dto.nomeCoordenador());
        projetoExistente.setDataInicio(dto.dataInicio());
        projetoExistente.setDataTermino(dto.dataTermino().orElse(null));
        projetoExistente.setValor(dto.valor().orElse(null));
        projetoExistente.setContratante(dto.contratante().orElse(null));
        projetoRepository.save(projetoExistente);
    }

    // Função auxiliar para remover campos ocultos
    private void removerCamposOcultos(Projeto projeto) {
        if (projeto.getCamposOcultos() != null) {
            String[] camposOcultos = projeto.getCamposOcultos().split(", ");
            for (String campo : camposOcultos) {
                switch (campo.trim().toLowerCase()) {
                    case "titulo" -> projeto.setTitulo(null);
                    case "contratante" -> projeto.setContratante(null);
                    case "status" -> projeto.setStatus(null);
                    case "nomecoordenador" -> projeto.setNomeCoordenador(null);
                    case "valor" -> projeto.setValor(null);
                    case "objetivo" -> projeto.setObjetivo(null);
                    case "integrantes" -> projeto.setIntegrantes(null);
                    case "links" -> projeto.setLinks(null);
                    case "descricao" -> projeto.setDescricao(null);
                    case "objeto" -> projeto.setObjeto(null);
                }
            }
        }
    }

    // Função auxiliar para atualizar os campos de um projeto
    private void atualizarCamposProjeto(Projeto projeto, Projeto projetoAtualizado) {
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
        if (projetoAtualizado.getStatus() != null) {
            projeto.setStatus(projetoAtualizado.getStatus());
        }
        if (projetoAtualizado.getIntegrantes() != null) {
            projeto.setIntegrantes(projetoAtualizado.getIntegrantes());
        }
        if (projetoAtualizado.getObjetivo() != null) {
            projeto.setObjetivo(projetoAtualizado.getObjetivo());
        }
        if (projetoAtualizado.getLinks() != null) {
            projeto.setLinks(projetoAtualizado.getLinks());
        }
    }

    // Função auxiliar para atualizar lista de campos ocultos
    private String atualizarCamposOcultos(String camposOcultosExistente, String novosCamposOcultos) {
        List<String> camposExistentes = camposOcultosExistente != null && !camposOcultosExistente.isBlank()
                ? Arrays.asList(camposOcultosExistente.split(",\\s*")) : List.of();
        List<String> novosCampos = Arrays.asList(novosCamposOcultos.split(",\\s*"));
        List<String> camposAtualizados = camposExistentes.stream()
                .filter(campo -> !novosCampos.contains(campo))
                .collect(Collectors.toList());
        novosCampos.stream()
                .filter(campo -> !camposAtualizados.contains(campo))
                .forEach(camposAtualizados::add);
        return String.join(", ", camposAtualizados);
    }
}
