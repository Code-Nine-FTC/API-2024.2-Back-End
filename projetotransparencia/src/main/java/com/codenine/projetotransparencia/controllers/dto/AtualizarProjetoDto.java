// controllers/AtualizarProjetoDto.java
package com.codenine.projetotransparencia.controllers.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.Optional;

public record AtualizarProjetoDto(
        Long id, // Novo campo para o ID do projeto a ser atualizado
        @NotEmpty String projeto,
        Optional<byte[]> resumoPdf,
        Optional<byte[]> resumoExcel,
        Optional<byte[]> proposta,
        Optional<byte[]> contrato){
}
