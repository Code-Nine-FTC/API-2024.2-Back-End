package com.codenine.projetotransparencia.services;

import java.util.List;
import java.util.Optional;

import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenine.projetotransparencia.controllers.dto.CadastrarParceiroDto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarParceiroDto;
import com.codenine.projetotransparencia.entities.Parceiro;
import com.codenine.projetotransparencia.repository.ParceiroRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepository;

@Service
public class ParceiroService {

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public Long cadastrarParceiro(CadastrarParceiroDto cadastrarParceiroDto) {
        Parceiro parceiro = new Parceiro(
            cadastrarParceiroDto.nome(),
            cadastrarParceiroDto.cnpj(),
            cadastrarParceiroDto.email(),
            cadastrarParceiroDto.telefone()
        );
        if (cadastrarParceiroDto.classificacaoDemanda().isPresent()) {
            for (ClassificacaoDemanda classificacaoDemanda : cadastrarParceiroDto.classificacaoDemanda().get()) {
                parceiro.getClassificacaoDemandas().add(classificacaoDemanda);
            }
        }
        // Salva parceiro
        Parceiro parceiroCadastrado = parceiroRepository.save(parceiro);

        return parceiroCadastrado.getId();
    }

    public List<Parceiro> listarParceiros() {
        return parceiroRepository.findAll();
    }

    public Optional<Parceiro> buscarParceiroPorId(Long id) {
        return parceiroRepository.findById(id);
    }

    public Long atualizarParceiro(AtualizarParceiroDto atualizarParceiroDto, Long id) throws IllegalArgumentException {
        Optional<Parceiro> parceiroOptional = parceiroRepository.findById(id);
        if (parceiroOptional.isEmpty()) {
            throw new IllegalArgumentException("Parceiro não encontrado");
        }
        Parceiro parceiro = parceiroOptional.get();

        if (atualizarParceiroDto.nome().isPresent()) {
            parceiro.setNome(atualizarParceiroDto.nome().get());
        }
        if (atualizarParceiroDto.cnpj().isPresent()) {
            parceiro.setCnpj(atualizarParceiroDto.cnpj().get());
        }
        if (atualizarParceiroDto.email().isPresent()) {
            parceiro.setEmail(atualizarParceiroDto.email().get());
        }
        if (atualizarParceiroDto.telefone().isPresent()) {
            parceiro.setTelefone(atualizarParceiroDto.telefone().get());
        }
        if (atualizarParceiroDto.classificacaoDemanda().isPresent()) {
            parceiro.getClassificacaoDemandas().add(atualizarParceiroDto.classificacaoDemanda().get());
        }

        parceiroRepository.save(parceiro);

        return parceiro.getId();
    }

    public void deletarParceiro(Long id) {
        parceiroRepository.deleteById(id);
    }
}