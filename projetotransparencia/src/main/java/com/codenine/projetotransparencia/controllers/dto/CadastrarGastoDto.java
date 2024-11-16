package com.codenine.projetotransparencia.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record CadastrarGastoDto(
    @NotEmpty String gasto,
    @NotEmpty Long idProjeto,
    Optional<MultipartFile> notaFiscal
    ){
}
