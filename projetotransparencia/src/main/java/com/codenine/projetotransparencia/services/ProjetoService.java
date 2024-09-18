// services/ProjetoService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.CadastrarProjetoDto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.controllers.AtualizarProjetoDto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.utils.documents.VerificarExcel;
import com.codenine.projetotransparencia.utils.documents.VerificarPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private VerificarExcel verificarExcel;

    @Autowired
    private VerificarPdf verificarPdf;

    public Long cadastrarProjeto(CadastrarProjetoDto cadastrarProjetoDto) {

        if (cadastrarProjetoDto.titulo().isEmpty() ||
            cadastrarProjetoDto.referenciaProjeto().isEmpty() ||
            cadastrarProjetoDto.empresa().isEmpty() ||
            cadastrarProjetoDto.objeto().isEmpty() ||
            cadastrarProjetoDto.nomeCoordenador().isEmpty() ||
            cadastrarProjetoDto.valor() == null ||
            cadastrarProjetoDto.dataInicio() == null ||
            cadastrarProjetoDto.dataTermino() == null) {
            throw new IllegalArgumentException("Algum campo obrigatório não foi preenchido");
        }

        if (cadastrarProjetoDto.resumoPdf().isPresent()) {
            if (!verificarPdf.verificar(cadastrarProjetoDto.resumoPdf().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um PDF");
            }
        }

//        if (cadastrarProjetoDto.resumoExcel().isPresent()) {
//            if (!verificarExcel.verificar(cadastrarProjetoDto.resumoExcel().get())) {
//                throw new IllegalArgumentException("O arquivo de resumo deve ser um Excel");
//            }
//        }

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

    public Long atualizarProjeto(AtualizarProjetoDto atualizarProjetoDto) {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(atualizarProjetoDto.id());

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + atualizarProjetoDto.id() + " não encontrado!");
        }

        Projeto projetoExistente = projetoOpcional.get();

        atualizarProjetoDto.titulo().ifPresent(projetoExistente::setTitulo);
        atualizarProjetoDto.referenciaProjeto().ifPresent(projetoExistente::setReferenciaProjeto);
        atualizarProjetoDto.empresa().ifPresent(projetoExistente::setEmpresa);
        atualizarProjetoDto.objeto().ifPresent(projetoExistente::setObjeto);
        atualizarProjetoDto.descricao().ifPresent(projetoExistente::setDescricao);
        atualizarProjetoDto.nomeCoordenador().ifPresent(projetoExistente::setNomeCoordenador);
        atualizarProjetoDto.valor().ifPresent(projetoExistente::setValor);
        atualizarProjetoDto.dataInicio().ifPresent(projetoExistente::setDataInicio);
        atualizarProjetoDto.dataTermino().ifPresent(projetoExistente::setDataTermino);
        atualizarProjetoDto.resumoPdf().ifPresent(projetoExistente::setResumoPdf);
        atualizarProjetoDto.resumoExcel().ifPresent(projetoExistente::setResumoExcel);

        projetoRepository.save(projetoExistente);
        return projetoExistente.getProjetoId();
    }

    public void deletarProjeto(Long id) {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(id);

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + id + " não encontrado!");
        }

        projetoRepository.deleteById(id);
    }
}
