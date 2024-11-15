package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.repository.BolsistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BolsistaService {

    @Autowired
    private BolsistaRepository bolsistaRepository;

    public List<Bolsista> listarBolsista() {
        return bolsistaRepository.findAll();
    }

    public Optional<Bolsista> buscarBolsistaPorId(Long id) {
        return bolsistaRepository.findById(id);
    }

}
