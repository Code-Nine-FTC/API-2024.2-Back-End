// services/CoordenadorService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Coordenador;
import com.codenine.projetotransparencia.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public Coordenador createCoordenador(Coordenador coordenador) {
        return coordenadorRepository.save(coordenador);
    }

    public List<Coordenador> getAllCoordenadores() {
        return coordenadorRepository.findAll();
    }

    public Coordenador getCoordenadorById(Long id) {
        return coordenadorRepository.findById(id).orElse(null);
    }
}
