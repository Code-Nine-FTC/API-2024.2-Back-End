// controllers/AtualizarProjetoDto.java
package com.codenine.projetotransparencia.controllers.dto;

import java.util.Date;
import java.util.Optional;

public record AtualizarProjetoDto(
        Long id, // Novo campo para o ID do projeto a ser atualizado
        Optional<String> titulo,
        Optional<String> referenciaProjeto,
        Optional<String> empresa,
        Optional<String> objeto,
        Optional<String> descricao,
        Optional<String> nomeCoordenador,
        Optional<Double> valor,
        Optional<Date> dataInicio,
        Optional<Date> dataTermino,
        Optional<byte[]> resumoPdf,
        Optional<byte[]> resumoExcel
) {
}
