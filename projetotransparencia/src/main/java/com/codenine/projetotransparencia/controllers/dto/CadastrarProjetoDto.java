package com.codenine.projetotransparencia.controllers.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;

public record CadastrarProjetoDto (
        @NotEmpty String titulo,
        @NotEmpty String referencia,
        @NotEmpty String nomeCoordenador,
        @NotEmpty Date dataInicio,
        Optional<Double> valor,
        Optional<Date> dataTermino,
        Optional<String> contratante) {

}