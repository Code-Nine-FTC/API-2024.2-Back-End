package com.codenine.projetotransparencia.controllers.dto;


import jakarta.validation.constraints.NotEmpty;

public record CadastrarBolsistaDto (
        @NotEmpty String nome,
        @NotEmpty String documento,
        @NotEmpty String RG,
        @NotEmpty String tipoBolsa,
        @NotEmpty String duracaoBolsa,
        @NotEmpty String areaAtuacao,
        @NotEmpty Long idProjeto
) {

}
