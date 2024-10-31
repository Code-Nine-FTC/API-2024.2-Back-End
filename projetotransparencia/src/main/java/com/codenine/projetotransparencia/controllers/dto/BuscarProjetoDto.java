package com.codenine.projetotransparencia.controllers.dto;

import java.util.Date;

public record BuscarProjetoDto(
        String referencia,
        String nomeCoordenador,
        Date dataInicio,
        Date dataTermino,
        Double valor,
        String status,
        String keyword
) {

}