package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "convenio")
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convenio_id")
    private Long id;

    @Column(nullable = false)
    private String nomeInstituicao;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private LocalDate dataInicial;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate dataFinal;

    @Lob
    @Column(nullable = true)
    private String documentoClausulas;

    @OneToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    @JsonIgnore
    private Projeto projeto;

    public Convenio() {
        // Construtor padrão para JPA
    }

    public Convenio(String nomeInstituicao, LocalDate dataInicial, LocalDate dataFinal, String documentoClausulas) {
        this.nomeInstituicao = nomeInstituicao;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.documentoClausulas = documentoClausulas;
    }
}