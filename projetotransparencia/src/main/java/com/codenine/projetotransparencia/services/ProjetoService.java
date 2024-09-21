// services/ProjetoService.java
package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.CadastrarProjetoDto;
import com.codenine.projetotransparencia.controllers.dto.BuscarProjetoDto;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.controllers.dto.AtualizarProjetoDto;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.utils.documents.VerificarExcel;
import com.codenine.projetotransparencia.utils.documents.VerificarPdf;
import com.codenine.projetotransparencia.utils.documents.VerificarTamanho;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
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

    @Autowired
    private VerificarTamanho verificarTamanho;

    @Autowired
    private ObjectMapper objectMapper;

    public Long cadastrarProjeto(CadastrarProjetoDto cadastrarProjetoDto) throws IOException {

        Projeto projeto = objectMapper.readValue(cadastrarProjetoDto.projeto(), Projeto.class);

        if (cadastrarProjetoDto.resumoPdf().isPresent()) {
            if (!verificarPdf.verificar(cadastrarProjetoDto.resumoPdf().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um PDF ou Excel");
            }
            else {
                if (!verificarTamanho.verificar(cadastrarProjetoDto.resumoPdf().get())) {
                    throw new IllegalArgumentException("O arquivo de resumo deve ter no máximo 5MB");
                }
                else {
                    projeto.setResumoPdf(cadastrarProjetoDto.resumoPdf().get());
                }
            }
        }
        if (cadastrarProjetoDto.resumoExcel().isPresent()) {
            if (!verificarExcel.verificar(cadastrarProjetoDto.resumoExcel().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um Pdf ou Excel");
            }
            else {
                if (!verificarTamanho.verificar(cadastrarProjetoDto.resumoExcel().get())) {
                    throw new IllegalArgumentException("O arquivo de resumo deve ter no máximo 5MB");
                }
                else {
                    projeto.setResumoExcel(cadastrarProjetoDto.resumoExcel().get());
                }
            }
        }

        if (cadastrarProjetoDto.proposta().isPresent()) {
            if (!verificarPdf.verificar(cadastrarProjetoDto.proposta().get())) {
                throw new IllegalArgumentException("O arquivo de proposta deve ser um PDF");
            }
            else {
                if (!verificarTamanho.verificar(cadastrarProjetoDto.proposta().get())) {
                    throw new IllegalArgumentException("O arquivo de proposta deve ter no máximo 5MB");
                }
                else {
                    projeto.setProposta(cadastrarProjetoDto.proposta().get());
                }
            }
        }

        if (cadastrarProjetoDto.contrato().isPresent()) {
            if (!verificarPdf.verificar(cadastrarProjetoDto.contrato().get())) {
                throw new IllegalArgumentException("O arquivo de contrato deve ser um PDF");
            }
            else {
                if (!verificarTamanho.verificar(cadastrarProjetoDto.contrato().get())) {
                    throw new IllegalArgumentException("O arquivo de contrato deve ter no máximo 5MB");
                }
                else {
                    projeto.setContrato(cadastrarProjetoDto.contrato().get());
                }
            }
        }

        return projetoRepository.save(projeto).getProjetoId();
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public List<Projeto> buscarProjetos(BuscarProjetoDto filtro) {

        String referenciaProjeto = filtro.referenciaProjeto();
        String nomeCoordenador = filtro.nomeCoordenador();
        String dataInicio = filtro.dataInicio();
        String dataTermino = filtro.dataTermino();
        Double valor = filtro.valor();

        if (StringUtils.hasText(referenciaProjeto) ||
                StringUtils.hasText(nomeCoordenador) ||
                StringUtils.hasText(dataInicio) ||
                StringUtils.hasText(dataTermino)
        ) {
            // Filtra os projetos com base nos parâmetros fornecidos
            return projetoRepository.findByFiltros(referenciaProjeto, nomeCoordenador, dataInicio, dataTermino, valor);
        } else {
            // Se nenhum filtro foi passado, retorna todos os projetos
            return projetoRepository.findAll();
        }
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
