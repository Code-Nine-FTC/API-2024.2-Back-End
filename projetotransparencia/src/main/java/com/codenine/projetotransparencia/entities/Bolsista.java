package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bolsista")
public class Bolsista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bolsista_id")
    private Long id;

    // Relacionamento ManyToOne com Projeto
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)  // Associa a chave estrangeira para o Projeto
    @JsonIgnore
    private Projeto projeto;  // Relacionamento ManyToOne com Projeto

    // Campos do bolsista
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento; // CPF ou CNPJ

    @Column(nullable = false)
    private String rg; // Registro geral

    @Column(nullable = false)
    private String tipoBolsa;

    @Column(nullable = false)
    private String duracao;

    @Column(nullable = false)
    private String areaAtuacao;

    // Construtores
    public Bolsista() {
    }

    public Bolsista( String nome, String documento, String rg, String tipoBolsa,
                    String duracao, String areaAtuacao, Projeto projeto) {
        this.nome = nome;
        this.documento = documento;
        this.rg = rg;
        this.tipoBolsa = tipoBolsa;
        this.duracao = duracao;
        this.areaAtuacao = areaAtuacao;
        this.projeto = projeto;
    }
}
