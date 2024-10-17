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
        // Chama o método de contagem de projetos dinâmicos com base nos filtros
        List<Projeto> projetosFiltrados = projetoRepositoryCustomImpl.buscarProjetos(
                coordenador, dataInicio, dataTermino, valorMaximo, valorMinimo, situacaoProjeto, tipoBusca, contratante);

        // Agrupa por ano (usando a lógica de intervalo entre dataInicio e dataTermino)
        return agruparProjetosPorAno(projetosFiltrados);
    }

    // Agrupamento por ano a partir da lista de projetos filtrados
    private Map<String, Long> agruparProjetosPorAno(List<Projeto> projetos) {
        Map<String, Long> projetosPorAno = new HashMap<>();

        for (Projeto projeto : projetos) {
            LocalDate dataInicioProjeto = projeto.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataTerminoProjeto = projeto.getDataTermino() != null
                    ? projeto.getDataTermino().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
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

}
