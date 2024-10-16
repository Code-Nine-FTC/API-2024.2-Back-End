package com.codenine.projetotransparencia.services;


import com.codenine.projetotransparencia.repository.ProjetoRepositoryCustomImpl; // Importando o repositório customizado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {


    @Autowired
    private ProjetoRepositoryCustomImpl projetoRepositoryCustomImpl; // Injetando o repositório customizado

    

    // Novo método para contar projetos dinamicamente
    public Map<String, Long> contarProjetosDinamicos(String coordenador, String dataInicio, String dataTermino,
                                                      String valorMaximo, String valorMinimo,
                                                      String situacaoProjeto, String tipoBusca, String contratante) {
        Long quantidade = projetoRepositoryCustomImpl.contarProjetosDinamicos(coordenador, dataInicio, dataTermino,
                                                                            valorMaximo, valorMinimo,
                                                                            situacaoProjeto, tipoBusca, contratante);
        Map<String, Long> resultado = new HashMap<>();
        resultado.put("quantidade", quantidade);
        return resultado;
    }
    public Map<String, Long> contarProjetosPorCategoria() {
        Long emAndamento = projetoRepositoryCustomImpl.contarProjetosPorSituacao("Em andamento");
        Long concluidos = projetoRepositoryCustomImpl.contarProjetosPorSituacao("Concluído");

        Map<String, Long> resultado = new HashMap<>();
        resultado.put("Em andamento", emAndamento);
        resultado.put("Concluído", concluidos);

        return resultado;
    }

}
