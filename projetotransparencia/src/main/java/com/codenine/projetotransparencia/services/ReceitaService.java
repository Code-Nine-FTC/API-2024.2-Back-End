package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Material;
import com.codenine.projetotransparencia.entities.Receita;
import com.codenine.projetotransparencia.repository.ReceitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitasRepository receitasRepository;

    public List<Receita> listarReceitas() {
        return receitasRepository.findAll();
    }

    public Optional<Receita> buscarReceitaPorId(Long id) {
        return receitasRepository.findById(id);
    }
}
