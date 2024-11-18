package com.codenine.projetotransparencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.controllers.dto.CadastrarBolsistaDto;
import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.repository.BolsistaRepository;
import com.codenine.projetotransparencia.entities.Projeto;
@Service
public class BolsistaService {

    @Autowired
    private BolsistaRepository bolsistaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public Long cadastrarBolsista(CadastrarBolsistaDto cadastrarBolsistaDto) {
        Long id = cadastrarBolsistaDto.idProjeto();
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));
                
        Bolsista bolsista = new Bolsista(

            cadastrarBolsistaDto.nome(),
            cadastrarBolsistaDto.documento(),
            cadastrarBolsistaDto.RG(),
            cadastrarBolsistaDto.tipoBolsa(),
            cadastrarBolsistaDto.duracaoBolsa(),
            cadastrarBolsistaDto.areaAtuacao(),
            projeto
        );
    
        // Salvar o bolsista
        Bolsista bolsistaCadastrado = bolsistaRepository.save(bolsista);
    
        return bolsistaCadastrado.getId();
    }

    public List<Bolsista> listarBolsista() {
        return bolsistaRepository.findAll();
    }

    public Optional<Bolsista> buscarBolsistaPorId(Long id) {
        return bolsistaRepository.findById(id);
    }

}
