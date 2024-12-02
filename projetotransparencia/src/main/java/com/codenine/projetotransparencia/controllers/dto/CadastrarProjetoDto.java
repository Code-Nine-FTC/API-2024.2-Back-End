package com.codenine.projetotransparencia.controllers.dto;

import java.time.LocalDate;
import java.util.Date;

import com.codenine.projetotransparencia.entities.Bolsista;
import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import com.codenine.projetotransparencia.entities.Convenio;
import com.codenine.projetotransparencia.entities.Parceiro;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Optional;

public record CadastrarProjetoDto (
        @NotEmpty String titulo,
        @NotEmpty String referencia,
        @NotEmpty String nomeCoordenador,
        @NotEmpty LocalDate dataInicio,
        Optional<Double> valor,
        Optional<LocalDate> dataTermino,
//        Optional<String> contratante,
        Optional<String> status,
        Optional<String> integrantes,
        Optional<String> objetivo,
        Optional<String> links,
        Optional<String> camposOcultos,
        Optional<Parceiro> parceiro,
        Optional<ClassificacaoDemanda> classificacaoDemanda,
        Optional<List<Bolsista>> bolsistas,
        Optional<Convenio> convenio
) {

}