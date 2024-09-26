package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.controllers.dto.BaixarDocumentoDto;
import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/listar")
    public List<Documento> listarDocumentos() {
        return documentoService.listarDocumentos();
    }

    @GetMapping("/visualizar/{id}")
    public Documento visualizarDocumento(@PathVariable Long id) {
        return documentoService.visualizarDocumento(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> baixarDocumento(@PathVariable Long id) {
        try {
            BaixarDocumentoDto resource = documentoService.baixarDocumento(id);
            if (resource == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.nomeOriginal() + "\"")
                    .body(resource.resource());
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirDocumento(@PathVariable Long id) {
        documentoService.excluirDocumento(id);
    }
}

