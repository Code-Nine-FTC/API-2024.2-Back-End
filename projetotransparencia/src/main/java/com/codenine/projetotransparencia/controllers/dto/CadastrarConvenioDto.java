package com.codenine.projetotransparencia.controllers.dto;

import jakarta.validation.constraints.NotEmpty;

public record CadastrarConvenioDto(
        @NotEmpty String nome,
        @NotEmpty String cnpj,
        @NotEmpty String email,
        @NotEmpty String telefone,
        @NotEmpty String areaColaboracao,
        String historicoParceria
) {}
