package com.codenine.projetotransparencia.controllers.dto;

import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;

import java.util.Optional;



public record  AtualizarParceiroDto (
        Optional<String> nome,
        Optional<String> cnpj,
        Optional<String> email,
        Optional<String> telefone,
        Optional<ClassificacaoDemanda> classificacaoDemanda
) {
}