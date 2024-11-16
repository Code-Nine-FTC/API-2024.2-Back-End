package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

//    // Relacionamento com o Projeto (ManyToOne)
//    @ManyToOne
//    @JoinColumn(name = "projeto_id", nullable = false)  // Associa a chave estrangeira para o Projeto
//    private Projeto projeto;  // Relacionamento ManyToOne com Projeto

    // Campos
    @Column(nullable = false)
    private String documento; // CPF ou CNPJ

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false)
    private String fornecedor;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Double valor;

    @OneToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    // Relacionamento com Documento (OneToMany)
    @OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> notaFiscal = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    // Construtores
    public Gasto() {
    }

    public Gasto( String documento, LocalDate data, Double valor, Material material) {
        this.documento = documento;
        this.data = data;
        this.valor = valor;
        this.material = material;
    }
}