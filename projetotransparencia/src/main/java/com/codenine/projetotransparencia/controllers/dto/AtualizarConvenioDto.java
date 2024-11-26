package com.codenine.projetotransparencia.controllers.dto;

import java.util.Optional;

public record  AtualizarConvenioDto (
        Optional<String> nome,
        Optional<String> cnpj,
        Optional<String> email,
        Optional<String> telefone,
        Optional<String> areaColaboracao
) {
}
