package com.codenine.projetotransparencia.controllers.dto;

import java.time.LocalDate;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;

public record CadastrarProjetoDto (
        @NotEmpty String titulo,
        @NotEmpty String referencia,
        @NotEmpty String nomeCoordenador,
        @NotEmpty LocalDate dataInicio,
        Optional<Double> valor,
        Optional<LocalDate> dataTermino,
        Optional<String> contratante,
        Optional<String> status,
        Optional<String> integrantes,
        Optional<String> objetivo,
        Optional<String> links,
        Optional<String> camposOcultos
) {

}