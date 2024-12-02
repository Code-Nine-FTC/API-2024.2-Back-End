package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.BaixarDocumentoDto;
import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.DocumentoRepository;
import com.codenine.projetotransparencia.repository.ProjetoRepository;
import com.codenine.projetotransparencia.utils.documents.SalvarDocumento;

import com.codenine.projetotransparencia.utils.documents.VerificarExcel;
import com.codenine.projetotransparencia.utils.documents.VerificarPdf;
import com.codenine.projetotransparencia.utils.documents.VerificarTamanho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private VerificarPdf verificarPdf;

    @Autowired
    private VerificarExcel verificarExcel;

    @Autowired
    private VerificarTamanho verificarTamanho;

    @Autowired
    private SalvarDocumento salvarDocumento;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    private final String relativePath = "projetotransparencia/src/main/java/com/codenine/projetotransparencia/uploads";

    public void uploadDocumento(MultipartFile documento, Object entidade, String tipoDocumento) throws IOException {
        String workingDir = System.getProperty("user.dir");
        String caminho = workingDir + File.separator + relativePath;

        if (!verificarTamanho.verificar(documento.getBytes())) {
            throw new IllegalArgumentException("O arquivo de resumo deve ter no máximo 5MB");
        }

        if ("resumoExcel".equals(tipoDocumento)) {
            if (!verificarExcel.verificar(documento.getBytes())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um Pdf ou Excel");
            }
        } else {
            if (!verificarPdf.verificar(documento.getBytes())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um PDF ou Excel");
            }
        }
        if (!documento.isEmpty()) {
            String caminhoArquivo = salvarDocumento.salvar(documento, caminho);
            Documento documentoSalvar = new Documento();
            documentoSalvar.setNome(documento.getOriginalFilename());
            documentoSalvar.setCaminho(caminhoArquivo);
            documentoSalvar.setTipo(tipoDocumento);
            documentoSalvar.setTamanho(documento.getSize());

            if (entidade instanceof Projeto) {
                documentoSalvar.setProjeto((Projeto) entidade);
                ((Projeto) entidade).getDocumentos().add(documentoSalvar);
            } else if (entidade instanceof Convenio) {
                documentoSalvar.setConvenio((Convenio) entidade);
                ((Convenio) entidade).getDocumentoClausulas().add(documentoSalvar);
            }
//            } else if (entidade instanceof Receita) {
//                documentoSalvar.setReceita((Receita) entidade);
//                ((Receita) entidade).getRubrica().add(documentoSalvar);
//            }

            documentoRepository.save(documentoSalvar);
        };
    }

    public List<Documento> listarDocumentos() {
        return documentoRepository.findAll();
    }

    public Documento visualizarDocumento(Long documentoId) {
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoId);
        return documentoOptional.orElse(null);
    }

    public BaixarDocumentoDto baixarDocumento(Long documentoId) throws MalformedURLException {
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoId);
        if (documentoOptional.isEmpty()) {
            return null;
        }
        Documento documento = documentoOptional.get();
        Path caminhoArquivo = Paths.get(relativePath).resolve(documento.getCaminho()).normalize();
        Resource resource = new UrlResource(caminhoArquivo.toUri());
        if (resource.exists()) {
            return new BaixarDocumentoDto(resource, documento.getNome());
        } else {
            return null;
        }
    }

    public void excluirDocumento(Long documentoId) {
        try {
            Optional<Documento> documentoOptional = documentoRepository.findById(documentoId);
            if (documentoOptional.isPresent()) {
                Documento documento = documentoOptional.get();
                Projeto projeto = projetoRepository.findById(documento.getProjeto().getId()).orElse(null);
                if (projeto != null) {
                    //                projeto.getDocumentos().remove(documento);
                    //                projetoRepository.save(projeto);
                    documento.setProjeto(null); // Remove the relation with the projeto
                    documentoRepository.save(documento); // Save the updated documento
                    auditoriaService.registrarExclusaoArquivo(projeto.getId(), documento.getId());
                } else {
                    throw new IllegalArgumentException("Projeto não encontrado para o documento com ID " + documentoId);
                }
            } else {
                throw new IllegalArgumentException("Documento com ID " + documentoId + " não encontrado!");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
