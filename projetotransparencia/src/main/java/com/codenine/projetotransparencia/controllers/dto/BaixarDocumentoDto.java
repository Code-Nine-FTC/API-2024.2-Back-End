package com.codenine.projetotransparencia.controllers.dto;

import org.springframework.core.io.Resource;

public record BaixarDocumentoDto(
        Resource resource,
        String nomeOriginal
) {
}
