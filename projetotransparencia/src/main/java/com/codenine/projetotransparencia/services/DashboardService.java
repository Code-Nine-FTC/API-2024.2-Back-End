package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private ProjetoRepositoryCustomImpl projetoRepositoryCustomImpl;

    public Map<String, Long> contarProjetosDinamicos(String coordenador, String dataInicio, String dataTermino,
                                                     String valorMaximo, String valorMinimo,
                                                     String situacaoProjeto, String tipoBusca, String contratante) {
        List<Projeto> projetosFiltrados = projetoRepositoryCustomImpl.buscarProjetos(
                coordenador, dataInicio, dataTermino, valorMaximo, valorMinimo, situacaoProjeto, tipoBusca, contratante);

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

    private int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    private LocalDate convertToLocalDate(java.sql.Date date) {
        return date.toLocalDate();
    }
}