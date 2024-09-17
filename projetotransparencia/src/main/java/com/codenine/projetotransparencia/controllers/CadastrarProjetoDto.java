package com.codenine.projetotransparencia.controllers;

import java.util.Date;
import java.util.Optional;

public record CadastrarProjetoDto(
        String titulo,
        String referenciaProjeto,
        String empresa, String objeto,
        Optional<String> descricao,
        String nomeCoordenador,
        Double valor,
        Date dataInicio,
        Date dataTermino,
        Optional<byte[]> resumoPdf,
        Optional<byte[]> resumoExcel) {
}
