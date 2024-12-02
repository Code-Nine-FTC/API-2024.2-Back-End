package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private LocalDate dataInicial;

    @Column(nullable = true)
    private LocalDate dataFinal;

    @OneToMany(mappedBy = "convenio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentoClausulas = new ArrayList<>();

    @OneToMany(mappedBy = "convenio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Projeto> projeto = new ArrayList<>();

    public Convenio() {
        // Construtor padrão para JPA
    }

    public Convenio(String nomeInstituicao, LocalDate dataInicial, LocalDate dataFinal, String documentoClausulas) {
        this.nomeInstituicao = nomeInstituicao;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Convenio(Convenio convenio) {
        this.nomeInstituicao = convenio.getNomeInstituicao();
        this.dataInicial = convenio.getDataInicial();
        this.dataFinal = convenio.getDataFinal();
    }
}