package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.controllers.dto.BaixarDocumentoDto;
import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.entities.Projeto;
import com.codenine.projetotransparencia.repository.DocumentoRepository;
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

    private final String relativePath = "projetotransparencia/src/main/java/com/codenine/projetotransparencia/uploads";

    public void uploadDocumento(MultipartFile documento, Projeto projeto, String tipoDocumento) throws IOException {
        String workingDir = System.getProperty("user.dir");
        String caminho = workingDir + File.separator + relativePath;

        if (!verificarTamanho.verificar(documento.getBytes())) {
            throw new IllegalArgumentException("O arquivo de resumo deve ter no máximo 5MB");
        }

        if ("resumoExcel".equals(tipoDocumento)) {
            if (!verificarExcel.verificar(documento.getBytes())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um Pdf ou Excel");
            }
            else {
                if (!documento.isEmpty()) {
                    String caminhoArquivo = salvarDocumento.salvar(documento, caminho);
                    Documento documentoSalvar = new Documento();
                    documentoSalvar.setNome(documento.getOriginalFilename());
                    documentoSalvar.setCaminho(caminhoArquivo);
                    documentoSalvar.setTipo(tipoDocumento);
                    documentoSalvar.setTamanho(documento.getSize());
                    documentoSalvar.setProjeto(projeto);
                    documentoRepository.save(documentoSalvar);
                }
            }
        }
        else {
            if (!verificarPdf.verificar(documento.getBytes())) {
                throw new IllegalArgumentException("O arquivo de resumo deve ser um PDF ou Excel");
            }
            else {
                if (!documento.isEmpty()) {
                    String uniqueNomeArquivo = salvarDocumento.salvar(documento, caminho);
                    Documento documentoSalvar = new Documento();
                    documentoSalvar.setNome(documento.getOriginalFilename());
                    documentoSalvar.setCaminho(uniqueNomeArquivo);
                    documentoSalvar.setTipo(tipoDocumento);
                    documentoSalvar.setTamanho(documento.getSize());
                    documentoSalvar.setProjeto(projeto);
                    documentoRepository.save(documentoSalvar);
                }
            }
        }
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
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoId);
        if (documentoOptional.isPresent()) {
            Documento documento = documentoOptional.get();
            File file = new File(documento.getCaminho());
            if (file.exists()) {
                file.delete();
            }
            documentoRepository.delete(documento);
        }
    }
}
