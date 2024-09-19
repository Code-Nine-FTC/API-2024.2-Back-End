package com.codenine.projetotransparencia.controllers.dto;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;

public record CadastrarProjetoDto (
        @NotEmpty String projeto,
        Optional<byte[]> resumoPdf,
        Optional<byte[]> resumoExcel,
        Optional<byte[]> proposta,
        Optional<byte[]> contrato){
}