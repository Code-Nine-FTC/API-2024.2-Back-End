package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.repository.ClassificacaoDemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ClassificacaoDemandaService {

    @Autowired
    private ClassificacaoDemandaRepository classificacaoDemandaRepository;

    public List<ClassificacaoDemanda> listarClassificacaoDemanda() {
        return classificacaoDemandaRepository.findAll();
    }

    public Optional<ClassificacaoDemanda> buscarClassificacaoDemandaPorId(Long id) {
        return classificacaoDemandaRepository.findById(id);
    }
}