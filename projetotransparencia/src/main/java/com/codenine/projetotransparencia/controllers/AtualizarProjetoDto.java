// controllers/AtualizarProjetoDto.java
package com.codenine.projetotransparencia.controllers;

import java.util.Date;
import java.util.Optional;

public record AtualizarProjetoDto(
        Long id, // Novo campo para o ID do projeto a ser atualizado
        String titulo,
        String referenciaProjeto,
        String empresa,
        String objeto,
        Optional<String> descricao,
        String nomeCoordenador,
        Double valor,
        Date dataInicio,
        Date dataTermino,
        Optional<byte[]> resumoPdf,
        Optional<byte[]> resumoExcel
) {
}
