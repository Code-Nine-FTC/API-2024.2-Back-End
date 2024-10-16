package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepositoryCustomImpl; // Importando o repositório customizado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.ZoneId;

@Service
public class DashboardService {


    @Autowired
    private ProjetoRepositoryCustomImpl projetoRepositoryCustomImpl; // Injetando o repositório customizado


    // Novo método para contar projetos dinamicamente
    public Map<String, Long> contarProjetosDinamicos(String coordenador, String dataInicio, String dataTermino,
                                                     String valorMaximo, String valorMinimo,
                                                     String situacaoProjeto, String tipoBusca, String contratante) {
        // Busca os projetos com os filtros dinâmicos
        List<Projeto> projetosFiltrados = projetoRepositoryCustomImpl.filtrarProjetos(coordenador, dataInicio, dataTermino,
                valorMaximo, valorMinimo,
                situacaoProjeto, tipoBusca, contratante);

        // Agrupa os projetos por ano com base no intervalo entre DataInicio e DataTermino
        return verificarAno(projetosFiltrados);
    }

    private Map<String, Long> verificarAno(List<Projeto> projetos) {
        Map<String, Long> projetosPorAno = new HashMap<>();

        for (Projeto projeto : projetos) {
            LocalDate dataInicio = projeto.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataTermino = projeto.getDataTermino() != null
                    ? projeto.getDataTermino().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    : dataInicio;

            int anoInicio = dataInicio.getYear();
            int anoTermino = dataTermino.getYear();

            for (int ano = anoInicio; ano <= anoTermino; ano++) {
                String chaveAno = String.valueOf(ano);
                projetosPorAno.put(chaveAno, projetosPorAno.getOrDefault(chaveAno, 0L) + 1);
            }
        }
        return projetosPorAno;
    }
}