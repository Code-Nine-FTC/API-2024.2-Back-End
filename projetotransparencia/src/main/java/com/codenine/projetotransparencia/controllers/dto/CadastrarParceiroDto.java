package com.codenine.projetotransparencia.controllers.dto;

import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Optional;

public record CadastrarParceiroDto (
        @NotEmpty String nome,
        @NotEmpty String cnpj,
        @NotEmpty String email,
        @NotEmpty String telefone,
        @JsonManagedReference Optional<List<ClassificacaoDemanda>> classificacaoDemanda
//        @NotEmpty Long idProjeto
) {
}