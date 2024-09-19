package com.codenine.projetotransparencia.controllers.dto;

public record BuscarProjetoDto(
        String referenciaProjeto,
        String nomeCoordenador,
        String dataInicio,
        String dataTermino
) {

}