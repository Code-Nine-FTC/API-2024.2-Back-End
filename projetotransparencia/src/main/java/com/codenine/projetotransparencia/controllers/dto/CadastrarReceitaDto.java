package com.codenine.projetotransparencia.controllers.dto;

import com.codenine.projetotransparencia.entities.Projeto;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

public record CadastrarReceitaDto(
        @NotEmpty Projeto projeto,
        @NotEmpty String nome,
        @NotEmpty String documento,
        @NotEmpty String parceiro,
        @NotEmpty LocalDate data,
        @NotEmpty Double valor
) {

}
