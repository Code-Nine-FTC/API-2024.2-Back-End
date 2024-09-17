// services/ProjetoService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.CadastrarProjetoDto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public Long cadastrarProjeto(CadastrarProjetoDto cadastrarProjetoDto) {
        var entidade = new Projeto(
                cadastrarProjetoDto.titulo(),
                cadastrarProjetoDto.referenciaProjeto(),
                cadastrarProjetoDto.empresa(),
                cadastrarProjetoDto.objeto(),
                cadastrarProjetoDto.descricao().orElse(null),
                cadastrarProjetoDto.nomeCoordenador(),
                cadastrarProjetoDto.valor(),
                cadastrarProjetoDto.dataInicio(),
                cadastrarProjetoDto.dataTermino(),
                cadastrarProjetoDto.resumoPdf().orElse(null),
                cadastrarProjetoDto.resumoExcel().orElse(null)
        );
        return projetoRepository.save(entidade).getProjetoId();
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto visualizarProjeto(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }
}
