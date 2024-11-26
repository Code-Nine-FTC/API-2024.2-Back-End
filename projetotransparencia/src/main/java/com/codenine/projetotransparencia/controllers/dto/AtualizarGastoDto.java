package com.codenine.projetotransparencia.controllers.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record AtualizarGastoDto(
        String gasto,
        Optional<MultipartFile> notaFiscal
) {
}
