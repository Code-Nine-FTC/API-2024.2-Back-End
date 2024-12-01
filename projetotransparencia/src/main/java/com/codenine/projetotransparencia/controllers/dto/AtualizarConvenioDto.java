package com.codenine.projetotransparencia.controllers.dto;

import java.time.LocalDate;
import java.util.Optional;

public record AtualizarConvenioDto(
        Optional<String> nomeInstituicao,
        Optional<LocalDate> dataInicial,
        Optional<LocalDate> dataFinal,
        Optional<String> documentoClausulas
) {}