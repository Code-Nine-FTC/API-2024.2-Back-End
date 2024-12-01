package com.codenine.projetotransparencia.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

public record CadastrarConvenioDto(
        @NotEmpty String nomeInstituicao,
        @NotEmpty LocalDate dataInicial,
        LocalDate dataFinal,
        String documentoClausulas
) {}