package com.codenine.projetotransparencia.controllers.dto;

public record CadastrarClassificacaoDemandaDto(
        String descricao,
        String statusAtendimento,
        String tipo,
        String prioridade
) {
}
