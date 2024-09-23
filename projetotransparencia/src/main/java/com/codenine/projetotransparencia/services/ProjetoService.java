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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
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

        Projeto projeto = new Projeto(
                cadastrarProjetoDto.titulo(),
                cadastrarProjetoDto.referencia(),
                cadastrarProjetoDto.contratante().orElse(null),
                null, // objeto
                null, // descricao
                cadastrarProjetoDto.coordenador(),
                cadastrarProjetoDto.valor().orElse(null),
                cadastrarProjetoDto.dataInicio(),
                cadastrarProjetoDto.dataTermino().orElse(null),
                null, // resumoPdf
                null, // resumoExcel
                null, // proposta
                null  // contrato
        );

        return projetoRepository.save(projeto).getId();
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public List<Projeto> buscarProjetos(BuscarProjetoDto filtro) {

        String referencia = filtro.referencia();
        String nomeCoordenador = filtro.nomeCoordenador();
        String dataInicio = filtro.dataInicio();
        String dataTermino = filtro.dataTermino();
        Double valor = filtro.valor();

        if (StringUtils.hasText(referencia) ||
                StringUtils.hasText(nomeCoordenador) ||
                StringUtils.hasText(dataInicio) ||
                StringUtils.hasText(dataTermino)
        ) {
            // Filtra os projetos com base nos parâmetros fornecidos
            return projetoRepository.findByFiltros(referencia, nomeCoordenador, dataInicio, dataTermino, valor);
        } else {
            // Se nenhum filtro foi passado, retorna todos os projetos
            return projetoRepository.findAll();
        }
    }

    public Projeto visualizarProjeto(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }

    public Long atualizarProjeto(AtualizarProjetoDto atualizarProjetoDto) throws JsonProcessingException {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(atualizarProjetoDto.id());

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + atualizarProjetoDto.id() + " não encontrado!");
        }

        Projeto projeto = projetoOpcional.get();
        if (atualizarProjetoDto.projeto() != null && !atualizarProjetoDto.projeto().isEmpty()) {
            Projeto projetoAtualizado;
            try {
                projetoAtualizado = objectMapper.readValue(atualizarProjetoDto.projeto(), Projeto.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao processar o JSON do projeto" + e.getMessage(), e);
            }

            if (projetoAtualizado.getTitulo() != null) {
                projeto.setTitulo(projetoAtualizado.getTitulo());
            }
            if (projetoAtualizado.getReferencia() != null) {
                projeto.setReferencia(projetoAtualizado.getReferencia());
            }
            if (projetoAtualizado.getContratante() != null) {
                projeto.setContratante(projetoAtualizado.getContratante());
            }
            if (projetoAtualizado.getObjeto() != null) {
                projeto.setObjeto(projetoAtualizado.getObjeto());
            }
            if (projetoAtualizado.getDescricao() != null) {
                projeto.setDescricao(projetoAtualizado.getDescricao());
            }
            if (projetoAtualizado.getNomeCoordenador() != null) {
                projeto.setNomeCoordenador(projetoAtualizado.getNomeCoordenador());
            }
            if (projetoAtualizado.getValor() != null) {
                projeto.setValor(projetoAtualizado.getValor());
            }
            if (projetoAtualizado.getDataInicio() != null) {
                projeto.setDataInicio(projetoAtualizado.getDataInicio());
            }
            if (projetoAtualizado.getDataTermino() != null) {
                projeto.setDataTermino(projetoAtualizado.getDataTermino());
            }
        }

//        BeanUtils.copyProperties(projetoAtualizado, projeto, getNullPropertyNames(projetoAtualizado));

        if (atualizarProjetoDto.resumoPdf().isPresent()) {
            if (!verificarPdf.verificar(atualizarProjetoDto.resumoPdf().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um PDF ou Excel");
            }
            else {
                if (!verificarTamanho.verificar(atualizarProjetoDto.resumoPdf().get())) {
                    throw new IllegalArgumentException("O arquivo de resumo deve ter no máximo 5MB");
                }
                else {
                    projeto.setResumoPdf(atualizarProjetoDto.resumoPdf().get());
                }
            }
        }
        if (atualizarProjetoDto.resumoExcel().isPresent()) {
            if (!verificarExcel.verificar(atualizarProjetoDto.resumoExcel().get())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um Pdf ou Excel");
            }
            else {
                if (!verificarTamanho.verificar(atualizarProjetoDto.resumoExcel().get())) {
                    throw new IllegalArgumentException("O arquivo de resumo deve ter no máximo 5MB");
                }
                else {
                    projeto.setResumoExcel(atualizarProjetoDto.resumoExcel().get());
                }
            }
        }

        if (atualizarProjetoDto.proposta().isPresent()) {
            if (!verificarPdf.verificar(atualizarProjetoDto.proposta().get())) {
                throw new IllegalArgumentException("O arquivo de proposta deve ser um PDF");
            }
            else {
                if (!verificarTamanho.verificar(atualizarProjetoDto.proposta().get())) {
                    throw new IllegalArgumentException("O arquivo de proposta deve ter no máximo 5MB");
                }
                else {
                    projeto.setProposta(atualizarProjetoDto.proposta().get());
                }
            }
        }

        if (atualizarProjetoDto.contrato().isPresent()) {
            if (!verificarPdf.verificar(atualizarProjetoDto.contrato().get())) {
                throw new IllegalArgumentException("O arquivo de contrato deve ser um PDF");
            }
            else {
                if (!verificarTamanho.verificar(atualizarProjetoDto.contrato().get())) {
                    throw new IllegalArgumentException("O arquivo de contrato deve ter no máximo 5MB");
                }
                else {
                    projeto.setContrato(atualizarProjetoDto.contrato().get());
                }
            }
        }



//        atualizarProjetoDto.titulo().ifPresent(projetoExistente::setTitulo);
//        atualizarProjetoDto.referenciaProjeto().ifPresent(projetoExistente::setReferenciaProjeto);
//        atualizarProjetoDto.empresa().ifPresent(projetoExistente::setContratante);
//        atualizarProjetoDto.objeto().ifPresent(projetoExistente::setObjeto);
//        atualizarProjetoDto.descricao().ifPresent(projetoExistente::setDescricao);
//        atualizarProjetoDto.nomeCoordenador().ifPresent(projetoExistente::setNomeCoordenador);
//        atualizarProjetoDto.valor().ifPresent(projetoExistente::setValor);
//        atualizarProjetoDto.dataInicio().ifPresent(projetoExistente::setDataInicio);
//        atualizarProjetoDto.dataTermino().ifPresent(projetoExistente::setDataTermino);
//        atualizarProjetoDto.resumoPdf().ifPresent(projetoExistente::setResumoPdf);
//        atualizarProjetoDto.resumoExcel().ifPresent(projetoExistente::setResumoExcel);

        projetoRepository.save(projeto);
        return projeto.getId();
    }


    public void deletarProjeto(Long id) {
        Optional<Projeto> projetoOpcional = projetoRepository.findById(id);

        if (projetoOpcional.isEmpty()) {
            throw new IllegalArgumentException("Erro: Projeto com ID " + id + " não encontrado!");
        }

        projetoRepository.deleteById(id);
    }
}

//

//        return projetoRepository.save(projeto).getId();
//    }
