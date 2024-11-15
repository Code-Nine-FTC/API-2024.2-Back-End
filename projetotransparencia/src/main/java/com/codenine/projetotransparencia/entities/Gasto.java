package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "gasto")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gasto_id")
    private Long id;

    // Relacionamento com o Projeto (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)  // Associa a chave estrangeira para o Projeto
    private Projeto projeto;  // Relacionamento ManyToOne com Projeto

    // Campos
    @Column(nullable = false)
    private String documento; // CPF ou CNPJ

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private String statusMaterial;

    // Relacionamento com Documento (OneToMany)
    @OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> notaFiscal = new ArrayList<>();

    // Construtores
    public Gasto() {
    }

    public Gasto( String documento, LocalDate data, Double valor, String statusMaterial) {
        this.documento = documento;
        this.data = data;
        this.valor = valor;
        this.statusMaterial = statusMaterial;
    }
}