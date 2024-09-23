package com.codenine.projetotransparencia.controllers.dto;

public record BuscarProjetoDto(
        String referencia,
        String nomeCoordenador,
        String dataInicio,
        String dataTermino,
        Double valor
) {

}