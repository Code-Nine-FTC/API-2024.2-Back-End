//package com.codenine.projetotransparencia.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "material")
//public class Material {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "material_id")
//    private Long id;
//
//    @Column(nullable = false)
//    private String nome;
//
//    @Column(nullable = false)
//    private Double valor;
//
//    @Column(nullable = false)
//    private String fornecedor;
//
//    @Column(nullable = false)
//    private String fornecedorEmail;
//
//    @Column(nullable = false)
//    private String fornecedorTelefone;
//
//    @Column(nullable = false)
//    private String statusUtilizacao;
//
//    // Relacionamento ManyToOne com Gasto
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "gasto_id", nullable = true)  // Associa a chave estrangeira para o Projeto
//    private Gasto gasto;  // Relacionamento ManyToOne com Projeto
//
//    // Construtores
//    public Material() {
//    }
//
//    public Material(String nome, Double valor, String fornecedor, String fornecedorEmail, String fornecedorTelefone, String statusUtilizacao) {
//        this.nome = nome;
//        this.valor = valor;
//        this.fornecedor = fornecedor;
//        this.fornecedorEmail = fornecedorEmail;
//        this.fornecedorTelefone = fornecedorTelefone;
//        this.statusUtilizacao = statusUtilizacao;
//    }
//}