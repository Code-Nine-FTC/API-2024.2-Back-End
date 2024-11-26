package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.CadastrarClassificacaoDemandaDto;
import com.codenine.projetotransparencia.entities.Auditoria;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.repository.ClassificacaoDemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ClassificacaoDemandaService {

    @Autowired
    private ClassificacaoDemandaRepository classificacaoDemandaRepository;

    // Listar todas as classificações de demanda
    public List<ClassificacaoDemanda> listarClassificacaoDemanda() {
        return classificacaoDemandaRepository.findAll();
    }

    // Buscar uma classificação de demanda por ID
    public Optional<ClassificacaoDemanda> buscarClassificacaoDemandaPorId(Long id) {
        return classificacaoDemandaRepository.findById(id);
    }

    // Cadastrar uma nova classificação de demanda
    public Long cadastrarClassificacaoDemanda(CadastrarClassificacaoDemandaDto classificacaoDemanda) {
        ClassificacaoDemanda classificacaoDemandaSalvar = new ClassificacaoDemanda();
        classificacaoDemandaSalvar.setDescricao(classificacaoDemanda.descricao());
        classificacaoDemandaSalvar.setStatusAtendimento(classificacaoDemanda.statusAtendimento());
        classificacaoDemandaSalvar.setTipo(classificacaoDemanda.tipo());
        classificacaoDemandaSalvar.setPrioridade(classificacaoDemanda.prioridade());
        return classificacaoDemandaRepository.save(classificacaoDemandaSalvar).getId();
    }

    // Editar uma classificação de demanda existente
    public ClassificacaoDemanda editarClassificacaoDemanda(Long id, ClassificacaoDemanda novaClassificacao) {
        // Verificar se a classificação de demanda existe
        Optional<ClassificacaoDemanda> classificacaoExistente = classificacaoDemandaRepository.findById(id);
        if (!classificacaoExistente.isPresent()) {
            return null;  // Se não existir, retorna null (ou pode lançar uma exceção)
        }

        // Atualizar os campos com os dados recebidos
        ClassificacaoDemanda classificacaoDemanda = classificacaoExistente.get();
        classificacaoDemanda.setDescricao(novaClassificacao.getDescricao());
        classificacaoDemanda.setStatusAtendimento(novaClassificacao.getStatusAtendimento());
        classificacaoDemanda.setTipo(novaClassificacao.getTipo());
        classificacaoDemanda.setPrioridade(novaClassificacao.getPrioridade());

        return classificacaoDemandaRepository.save(classificacaoDemanda);
    }

    // Excluir uma classificação de demanda pelo ID
    public boolean excluirClassificacaoDemanda(Long id) {
        if (classificacaoDemandaRepository.existsById(id)) {
            classificacaoDemandaRepository.deleteById(id);
            return true;
        }
        return false;  // Se não existir, retorna false
    }
}

