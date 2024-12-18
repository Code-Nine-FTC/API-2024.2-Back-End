package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ClassificacaoDemandaRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private ProjetoRepositoryCustomImpl projetoRepositoryCustomImpl;

    @Autowired
    private ClassificacaoDemandaRepository demandaRepository;

    public Map<String, Long> buscarDemandas(){
        Map<String, Long> demandas = new HashMap<>();
        for (ClassificacaoDemanda classificacaoDemanda : demandaRepository.findAll()) {
            demandas.put(classificacaoDemanda.getTipo(), classificacaoDemanda.getProjetos().stream().count());
        }
        return demandas;
    }

    public Map<String, Double> buscarValorAno(String coordenador){
        Map<String, Double> valorAno = new TreeMap<>();
        List<Projeto> projetos = projetoRepositoryCustomImpl.buscarProjetos(coordenador, null, null, null, null, null, null, null);

        for (Projeto projeto : projetos) {
            LocalDate dataInicioProjeto = projeto.getDataInicio();
            LocalDate dataTerminoProjeto = projeto.getDataTermino() != null
                    ? projeto.getDataTermino()
                    : dataInicioProjeto;

            int anoInicio = dataInicioProjeto.getYear();
            int anoTermino = dataTerminoProjeto.getYear();

            for (int ano = anoInicio; ano <= anoTermino; ano++) {
                String chaveAno = String.valueOf(ano);
                valorAno.put(chaveAno, valorAno.getOrDefault(chaveAno, 0.0) + projeto.getValor());
            }
        }
        return valorAno;
    }


    public Map<String, Long> contarProjetosDinamicos(String dataInicio, String dataTermino, String coordenador,
                                                     String valorMaximo, String valorMinimo,
                                                     String situacaoProjeto, String tipoBusca, String contratante) {
        try {
            // Verificar se o valor 'situacaoProjeto' é 'Todos' e substituir por null para não filtrar
            if ("Todos".equals(situacaoProjeto)) {
                situacaoProjeto = null;  // Remove o filtro de status
            }

            List<Projeto> projetosFiltrados = projetoRepositoryCustomImpl.buscarProjetos(
                    coordenador, dataInicio, dataTermino, valorMaximo, valorMinimo, situacaoProjeto, tipoBusca, contratante);

            if (dataInicio != null && dataTermino != null && !projetosFiltrados.isEmpty()) {
                // Converter as datas para verificar o intervalo
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate inicio = LocalDate.parse(dataInicio, formatter);
                LocalDate termino = LocalDate.parse(dataTermino, formatter);

                // Verificar se o intervalo é maior que um ano
                if (inicio.getYear() == termino.getYear()) {
                    // Se o intervalo estiver no mesmo ano, agrupar por meses
                    return agruparProjetosPorMesPorAno(projetosFiltrados, inicio, termino);
                } else {
                    // Se o intervalo abranger mais de um ano, agrupar por anos
                    return agruparProjetosPorAno(projetosFiltrados);
                }
            }

            // Caso não seja fornecida uma data específica, agrupar por ano
            return agruparProjetosPorAno(projetosFiltrados);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar projetos: " + e.getMessage(), e);
        }
    }

    private Map<String, Long> agruparProjetosPorAno(List<Projeto> projetos) {
        Map<String, Long> projetosPorAno = new HashMap<>();

        for (Projeto projeto : projetos) {
            LocalDate dataInicioProjeto = projeto.getDataInicio();
            LocalDate dataTerminoProjeto = projeto.getDataTermino() != null
                    ? projeto.getDataTermino()
                    : dataInicioProjeto;

            int anoInicio = dataInicioProjeto.getYear();
            int anoTermino = dataTerminoProjeto.getYear();

            for (int ano = anoInicio; ano <= anoTermino; ano++) {
                String chaveAno = String.valueOf(ano);
                projetosPorAno.put(chaveAno, projetosPorAno.getOrDefault(chaveAno, 0L) + 1);
            }
        }

        return projetosPorAno;
    }

    private Map<String, Long> agruparProjetosPorMesPorAno(List<Projeto> projetos, LocalDate dataInicio, LocalDate dataTermino) {
        Map<String, Long> projetosPorMes = new TreeMap<>();  // TreeMap para garantir a ordenação

        for (Projeto projeto : projetos) {
            LocalDate dataInicioProjeto = projeto.getDataInicio();
            LocalDate dataTerminoProjeto = projeto.getDataTermino() != null
                    ? projeto.getDataTermino()
                    : dataInicioProjeto;

            int anoInicioProjeto = dataInicioProjeto.getYear();
            int mesInicioProjeto = dataInicioProjeto.getMonthValue();
            int anoTerminoProjeto = dataTerminoProjeto.getYear();
            int mesTerminoProjeto = dataTerminoProjeto.getMonthValue();

            for (int ano = anoInicioProjeto; ano <= anoTerminoProjeto; ano++) {
                int mesInicio = (ano == anoInicioProjeto) ? mesInicioProjeto : 1;
                int mesTermino = (ano == anoTerminoProjeto) ? mesTerminoProjeto : 12;

                for (int mes = mesInicio; mes <= mesTermino; mes++) {
                    String chaveMes = ano + "-" + String.format("%02d", mes); // Usando ano e mês como chave
                    projetosPorMes.put(chaveMes, projetosPorMes.getOrDefault(chaveMes, 0L) + 1);
                }
            }
        }

        // Transformar a chave no formato "Mês de Ano"
        Map<String, Long> resultadosOrdenados = new LinkedHashMap<>();
        for (String chave : projetosPorMes.keySet()) {
            String[] partes = chave.split("-");
            int mes = Integer.parseInt(partes[1]);
            String mesNome = getMesNome(mes);
            String resultadoChave = mesNome + " de " + partes[0];
            resultadosOrdenados.put(resultadoChave, projetosPorMes.get(chave));
        }

        return resultadosOrdenados;
    }

//    private int getYearFromLocalDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.YEAR);
//    }
//
//    private int getMonthFromDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.MONTH) + 1;
//    }

    private String getMesNome(int mes) {
        String[] meses = {
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        return meses[mes - 1];
    }

//    private LocalDate convertToLocalDate(Date date) {
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//    }
}
