package com.codenine.projetotransparencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenine.projetotransparencia.controllers.dto.CadastrarBolsistaDto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarBolsistaDto;
import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.BolsistaRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
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

    public Long atualizarBolsista(AtualizarBolsistaDto atualizarBolsistaDto, Long id) throws IllegalArgumentException {
        Optional<Bolsista> bolsistaOptional = bolsistaRepository.findById(id);
        if (bolsistaOptional.isEmpty()) {
            throw new IllegalArgumentException("Bolsista não encontrado");
        }
        Bolsista bolsista = bolsistaOptional.get();

        if (atualizarBolsistaDto.nome().isPresent()) {
            bolsista.setNome(atualizarBolsistaDto.nome().get());
        }
       
        if (atualizarBolsistaDto.documento().isPresent()) {
            bolsista.setDocumento(atualizarBolsistaDto.documento().get());
        }

        if (atualizarBolsistaDto.rg().isPresent()) {
            bolsista.setRg(atualizarBolsistaDto.rg().get());
        }

        if (atualizarBolsistaDto.tipoBolsa().isPresent()) {
            bolsista.setTipoBolsa(atualizarBolsistaDto.tipoBolsa().get());
        }

        if (atualizarBolsistaDto.duracao().isPresent()) {
            bolsista.setDuracao(atualizarBolsistaDto.duracao().get());
        }

        if (atualizarBolsistaDto.areaAtuacao().isPresent()) {
            bolsista.setAreaAtuacao(atualizarBolsistaDto.areaAtuacao().get());
        }

        bolsistaRepository.save(bolsista);

        return bolsista.getId();
    }

    public void deletarBolsista(Long id) {
        projetoRepository.findAll().forEach(projeto -> {
            projeto.getBolsistas().removeIf(bolsista -> bolsista.getId().equals(id));
            projetoRepository.save(projeto);
        });

        bolsistaRepository.deleteById(id);
    }
}
