package com.codenine.projetotransparencia.controllers.dto;

public record UploadDocumentoDto(
        String nome,
        String caminho,
        String tipo,
        Long tamanho
) {
}
