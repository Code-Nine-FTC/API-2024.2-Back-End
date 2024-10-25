// controllers/AtualizarProjetoDto.java
package com.codenine.projetotransparencia.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Optional;

public record AtualizarProjetoDto(
        Long id, // Novo campo para o ID do projeto a ser atualizado
        @NotEmpty String projeto,
        Optional<MultipartFile> resumoPdf,
        Optional<MultipartFile> resumoExcel,
        Optional<MultipartFile> proposta,
        Optional<MultipartFile> contrato,
        Optional<String> camposOcultos){
}
