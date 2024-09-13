// services/AdministradorService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Administrador;
import com.codenine.projetotransparencia.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public Administrador createAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public List<Administrador> getAllAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador findByEmail(String email) {
        return administradorRepository.findByEmail(email);
    }
}
