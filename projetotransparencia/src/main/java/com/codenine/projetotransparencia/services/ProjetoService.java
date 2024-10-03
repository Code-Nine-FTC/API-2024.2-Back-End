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
        Date dataInicio = filtro.dataInicio();
        Date dataTermino = filtro.dataTermino();
        Double valor = filtro.valor();

        if (StringUtils.hasText(referencia) ||
                StringUtils.hasText(nomeCoordenador) ||
                dataInicio != null ||
                dataTermino != null) {
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

    // daqui para baixo é a normalização de dados, e função de salvar no banco. Eu preciso separar elas ainda
    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            salvarProjetosDoJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String normalizarData(String dataString) {
        // aqui esta sendo removido as barras das datas, e deixando os números, pq elas tão tudo errada
        String dataCorrigida = dataString.replaceAll("[^0-9]", "").trim();

        // aqui é feita uma contagem pra ver se tem 8 numeros mesmo após remover as barras
        if (dataCorrigida.length() != 8) {
            throw new IllegalArgumentException("Formato de data inválido: " + dataString);
        }

        // aqui é adicionado as barras na data novamente
        String dataFormatada = dataCorrigida.substring(0, 2) + "/" + dataCorrigida.substring(2, 4) + "/" + dataCorrigida.substring(4);

        // aqui temos que ver melhot, pq estamos pegando datas com 31 dias de meses com 30 e arrumando para o dia primeiro do seguinte mês
        String[] partes = dataFormatada.split("/");

        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        // aqui corrige o dia caso seja maior que o ultimo do mes
        int ultimoDiaDoMes = getUltimoDiaDoMes(mes, ano);

        if (dia > ultimoDiaDoMes) {
            dia = 1;  // muda para o primeiro dia do mes seguinte
            mes++;  // muda para o mes seguinte
            if (mes > 12) { // aqui se o mes for dezembro, ele muda para janeiro do ano seguinte
                mes = 1;
                ano++;
            }
        }

        // aqui é a formatação da data final
        String dataCorrigidaFinal = String.format("%02d/%02d/%04d", dia, mes, ano);
        return dataCorrigidaFinal;
    }

    private int getUltimoDiaDoMes(int mes, int ano) {
        //aqui retorna o ultimo dia do mês considerando o ano
        switch (mes) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31; // Meses com 31 dias
            case 4: case 6: case 9: case 11:
                return 30; // Meses com 30 dias
            case 2:
                // aqui é a verificação do ano bissexto, para o caso de 29 dias em fevereiro
                return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) ? 29 : 28;
            default:
                return 31;
        }
    }
    //Após o tratamento de dados aí em cima, aqui em baixo é a função que salva no bd, mas ainda vou ter que separar uma da outra pra ficar melhor NÃO ESQUECER DE MUDAR ISTO

    public void salvarProjetosDoJson() throws IOException, ParseException {
        // leitura do arquivo Json
        JsonNode projetosNode = objectMapper.readTree(new File("C:\\Users\\Jonas\\Desktop\\API-2024.2-Back-End\\raspagem-dados\\projects_data.json"));

        // Itera sobre os projetos dentro da lista no json
        for (JsonNode projetoNode : projetosNode) {
            String titulo = projetoNode.has("Referência do projeto") ? projetoNode.get("Referência do projeto").asText() : "Título não fornecido";
            String referencia = titulo;
            String nomeCoordenador = projetoNode.has("Coordenador") ? projetoNode.get("Coordenador").asText() : "Coordenador não informado";

            // tratamento das datas
            String dataInicioString = projetoNode.has("Data de início") ? projetoNode.get("Data de início").asText() : "";
            String dataTerminoString = projetoNode.has("Data de término") ? projetoNode.get("Data de término").asText() : "";

            // aqui normaliza as datas para o formato padrão que a gente ta usando no bd
            String dataInicioNormalizada = dataInicioString.isEmpty() ? "01/01/1900" : normalizarData(dataInicioString);
            String dataTerminoNormalizada = dataTerminoString.isEmpty() ? "01/01/1900" : normalizarData(dataTerminoString);

            // aqui converte a data para o formato date
            Date dataInicio = null;
            Date dataTermino = null;

            try {
                dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicioNormalizada);
                dataTermino = new SimpleDateFormat("dd/MM/yyyy").parse(dataTerminoNormalizada);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // também foi necessario tratar os valores, pq tinha muito numero com caracteres não reconhecidos par ao tipo double, então aqui faz um tratamento
            Optional<Double> valor = Optional.ofNullable(projetoNode.get("Valor do projeto").asText())
                    .map(v -> v.replace(",", ".")  // substitui vírgulas por ponto
                            .replaceAll("\\.(?=.*\\.)", "")  // remove pontos extras, mantendo apenas o primeiro
                            .replaceAll("[^0-9.-]", "")  // Remove qualquer caractere não numérico
                    )
                    .filter(val -> !val.isEmpty())  // verifica se o valor não está vazio
                    .map(val -> {
                        // Verifica se há mais de um ponto na string após a substituição
                        if (val.split("\\.").length > 2) {
                            throw new IllegalArgumentException("Erro ao processar valor: múltiplos pontos decimais detectados.");
                        }
                        return Double.parseDouble(val);  // Converte para Double
                    });

            Optional<String> contratante = Optional.of(projetoNode.has("Empresa") ? projetoNode.get("Empresa").asText() : "");

            CadastrarProjetoDto dto = new CadastrarProjetoDto(
                    titulo,
                    referencia,
                    nomeCoordenador,
                    dataInicio,
                    valor,
                    Optional.ofNullable(dataTermino),
                    contratante
            );
            cadastrarProjeto(dto);
        }
    }
}
