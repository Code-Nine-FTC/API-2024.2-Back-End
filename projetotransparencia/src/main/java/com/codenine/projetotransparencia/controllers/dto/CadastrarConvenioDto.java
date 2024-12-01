package com.codenine.projetotransparencia.controllers.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record CadastrarConvenioDto(
        Optional<String> documentoClausulas, // Campo para o JSON ou texto
        Optional<MultipartFile> arquivoDocumento // Campo para o arquivo
) {}
