package com.codenine.projetotransparencia.controllers.dto;

import java.util.Optional;



public record  AtualizarBolsistaDto (
    Optional<String> nome,
    Optional<String> documento,
    Optional<String> rg,
    Optional<String> tipoBolsa,
    Optional<String> duracao,
    Optional<String> areaAtuacao
    ) {
}
