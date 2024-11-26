package com.codenine.projetotransparencia.controllers.dto;

import java.util.Optional;

public record AtualizarDemandaDto(
        Optional<String> descricao,
        Optional<String> statusAtendimento,
        Optional<String> tipo,
        Optional<String> prioridade
) {
}
