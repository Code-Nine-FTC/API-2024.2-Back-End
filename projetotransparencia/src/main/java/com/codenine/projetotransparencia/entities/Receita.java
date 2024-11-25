package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "receita")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receita_id")
    private Long id;

    // Relacionamento ManyToOne com Projeto
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)  // Associa a chave estrangeira para o Projeto
    private Projeto projeto;  // Relacionamento ManyToOne com Projeto

    // Campos da receita
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento; // CPF ou CNPJ

    @Column(nullable = false)
    private String parceiro;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Double valor;

    // Relacionamento OneToMany com Documento (Rubrica)
    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> rubrica = new ArrayList<>();

    // Construtores
    public Receita() {
    }

    public Receita(String nome, String documento, String parceiro, LocalDate data, Double valor) {
        this.nome = nome;
        this.documento = documento;
        this.parceiro = parceiro;
        this.data = data;
        this.valor = valor;
    }

    public String getTipoDocumento() {
        return "";
    }
}
