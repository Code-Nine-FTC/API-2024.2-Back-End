// services/ProjetoService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.CadastrarProjetoDto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.utils.documents.VerificarExcel;
import com.codenine.projetotransparencia.utils.documents.VerificarPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private VerificarExcel verificarExcel;

    @Autowired
    private VerificarPdf verificarPdf;

    public Long cadastrarProjeto(CadastrarProjetoDto cadastrarProjetoDto) {
        if (cadastrarProjetoDto.resumoPdf().isPresent()) {
            if (!verificarPdf.verificar(cadastrarProjetoDto.resumoPdf().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um PDF");
            }
        }

        if (cadastrarProjetoDto.resumoExcel().isPresent()) {
            if (!verificarExcel.verificar(cadastrarProjetoDto.resumoExcel().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um Excel");
            }
        }

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
