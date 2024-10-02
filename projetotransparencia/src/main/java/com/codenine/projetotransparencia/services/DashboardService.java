package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;


@Service
public class DashboardService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public Map<String, Long> contarProjetosPorCoordenador(String nomeCoordenador) {
        long quantidade = projetoRepository.countByNomeCoordenador(nomeCoordenador);
        Map<String, Long> resultado = new HashMap<>();
        resultado.put("quantidade", quantidade);
        return resultado;
    }
}
