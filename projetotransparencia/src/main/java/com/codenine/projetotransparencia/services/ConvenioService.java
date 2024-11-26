package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.repository.ConvenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    public List<Convenio> listarConvenio() {
        return convenioRepository.findAll();
    }

    public Optional<Convenio> buscarConvenioPorId(Long id) {
        return convenioRepository.findById(id);
    }
}
