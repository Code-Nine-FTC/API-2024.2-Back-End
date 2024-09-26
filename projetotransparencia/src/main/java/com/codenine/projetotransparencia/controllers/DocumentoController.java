package com.codenine.projetotransparencia.controllers;

import com.codenine.projetotransparencia.entities.Documento;
import com.codenine.projetotransparencia.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/excluir/{id}")
    public void excluirDocumento(@PathVariable Long id) {
        documentoService.excluirDocumento(id);
    }
}

