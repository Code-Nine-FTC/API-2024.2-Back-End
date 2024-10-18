package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Projeto;
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

    public Map<String, Long> contarProjetosDinamicos(String dataInicio, String dataTermino, String coordenador,
                                                     String valorMaximo, String valorMinimo,
                                                     String situacaoProjeto, String tipoBusca, String contratante) {

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
                return agruparProjetosPorMesPorAno(projetosFiltrados, dataInicio, dataTermino);
            } else {
                // Se o intervalo abranger mais de um ano, agrupar por anos
                return agruparProjetosPorAno(projetosFiltrados);
            }
        }

        // Caso não seja fornecida uma data específica, agrupar por ano
        return agruparProjetosPorAno(projetosFiltrados);
    }

    private Map<String, Long> agruparProjetosPorAno(List<Projeto> projetos) {
        Map<String, Long> projetosPorAno = new HashMap<>();

        for (Projeto projeto : projetos) {
            Date dataInicioProjeto = projeto.getDataInicio();
            Date dataTerminoProjeto = projeto.getDataTermino() != null
                    ? projeto.getDataTermino()
                    : dataInicioProjeto;

            int anoInicio = getYearFromDate(dataInicioProjeto);
            int anoTermino = getYearFromDate(dataTerminoProjeto);

            for (int ano = anoInicio; ano <= anoTermino; ano++) {
                String chaveAno = String.valueOf(ano);
                projetosPorAno.put(chaveAno, projetosPorAno.getOrDefault(chaveAno, 0L) + 1);
            }
        }

        return projetosPorAno;
    }

    private Map<String, Long> agruparProjetosPorMesPorAno(List<Projeto> projetos, String dataInicio, String dataTermino) {
        Map<String, Long> projetosPorMes = new TreeMap<>();  // TreeMap para garantir a ordenação

        for (Projeto projeto : projetos) {
            Date dataInicioProjeto = projeto.getDataInicio();
            Date dataTerminoProjeto = projeto.getDataTermino() != null
                    ? projeto.getDataTermino()
                    : dataInicioProjeto;

            int anoInicioProjeto = getYearFromDate(dataInicioProjeto);
            int mesInicioProjeto = getMonthFromDate(dataInicioProjeto);
            int anoTerminoProjeto = getYearFromDate(dataTerminoProjeto);
            int mesTerminoProjeto = getMonthFromDate(dataTerminoProjeto);

            for (int ano = anoInicioProjeto; ano <= anoTerminoProjeto; ano++) {
                int mesInicio = (ano == anoInicioProjeto) ? mesInicioProjeto : 1;
                int mesTermino = (ano == anoTerminoProjeto) ? mesTerminoProjeto : 12;

                for (int mes = mesInicio; mes <= mesTermino; mes++) {
                    String chaveMes = getMesNome(mes);
                    projetosPorMes.put(chaveMes, projetosPorMes.getOrDefault(chaveMes, 0L) + 1);
                }
            }
        }

        return projetosPorMes;
    }

    private int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    private int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    private String getMesNome(int mes) {
        String[] meses = {
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        return meses[mes - 1];
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
