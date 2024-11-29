package com.codenine.projetotransparencia.controllers.dto;

import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import jakarta.validation.constraints.NotEmpty;

import java.util.Optional;

public record CadastrarParceiroDto (
        @NotEmpty String nome,
        @NotEmpty String cnpj,
        @NotEmpty String email,
        @NotEmpty String telefone,
        Optional<ClassificacaoDemanda> classificacaoDemanda
//        @NotEmpty Long idProjeto
) {
}