// services/ProjetoService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto createProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto getProjetoById(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }
}
