package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.entities.Gasto;
import com.codenine.projetotransparencia.entities.Receita;
import com.codenine.projetotransparencia.repository.PrestacaoContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codenine.projetotransparencia.entities.PrestacaoContas;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestacaoContasService {

    @Autowired
    private PrestacaoContasRepository prestacaoContasRepository;

    public List<PrestacaoContas> listarPrestacaoContas() {
        return prestacaoContasRepository.findAll();
    }

    public PrestacaoContas visualizarPrestacaoContas(Long id) {
        return prestacaoContasRepository.findById(id).orElse(null);
    }
}
