package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.repository.ConvenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codenine.projetotransparencia.controllers.dto.CadastrarConvenioDto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarConvenioDto;

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

    public Long cadastrarConvenio(CadastrarConvenioDto cadastrarConvenioDto) {
        Convenio convenio = new Convenio(
                cadastrarConvenioDto.nome(),
                cadastrarConvenioDto.cnpj(),
                cadastrarConvenioDto.email(),
                cadastrarConvenioDto.telefone(),
                cadastrarConvenioDto.areaColaboracao()
        );

        // Salva convenio
        Convenio convenioCadastrado = convenioRepository.save(convenio);

        return convenioCadastrado.getId();
    }

    public Long atualizarConvenio(AtualizarConvenioDto atualizarConvenioDto, Long id) throws IllegalArgumentException {
        Optional<Convenio> convenioOptional = convenioRepository.findById(id);
        if (convenioOptional.isEmpty()) {
            throw new IllegalArgumentException("Convenio não encontrado");
        }
        Convenio convenio = convenioOptional.get();

        if (atualizarConvenioDto.nome().isPresent()) {
            convenio.setNome(atualizarConvenioDto.nome().get());
        }
        if (atualizarConvenioDto.cnpj().isPresent()) {
            convenio.setCnpj(atualizarConvenioDto.cnpj().get());
        }
        if (atualizarConvenioDto.email().isPresent()) {
            convenio.setEmail(atualizarConvenioDto.email().get());
        }
        if (atualizarConvenioDto.telefone().isPresent()) {
            convenio.setTelefone(atualizarConvenioDto.telefone().get());
        }
        if (atualizarConvenioDto.areaColaboracao().isPresent()) {
            convenio.setAreaColaboracao(atualizarConvenioDto.areaColaboracao().get());
        }

        convenioRepository.save(convenio);

        return convenio.getId();
    }

    public void deletarConvenio(Long id) {
        convenioRepository.deleteById(id);
    }
}
