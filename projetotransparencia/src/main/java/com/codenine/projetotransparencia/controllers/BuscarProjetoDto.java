package com.codenine.projetotransparencia.controllers;

public record BuscarProjetoDto(
        String referenciaProjeto,
        String nomeCoordenador,
        String dataInicio,
        String dataTermino
) {

}