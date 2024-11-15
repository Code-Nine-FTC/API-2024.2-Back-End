package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.entities.Material;
import com.codenine.projetotransparencia.repository.MateriaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    private MateriaisRepository materiaisRepository;

    public List<Material> listarMaterial() {
        return materiaisRepository.findAll();
    }

    public Optional<Material> buscarMaterialPorId(Long id) {
        return materiaisRepository.findById(id);
    }
}
