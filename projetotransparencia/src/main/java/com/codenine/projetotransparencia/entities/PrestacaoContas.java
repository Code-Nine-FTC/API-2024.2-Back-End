//package com.codenine.projetotransparencia.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@Table(name = "prestacao_contas")
//public class PrestacaoContas {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "prestacao_id")
//    private Long id;
//
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "projeto_id")
//    private Projeto projeto;
//
//    @Column(nullable = false)
//    private String nome;
//
//    @Column(nullable = false)
//    private String documento;
//
//    @Column(nullable = false)
//    private String tipoDocumento;
//
//    @Column
//    private LocalDate data;
//
//    @Column(nullable = false)
//    private String tipoPrestacao;
//
//    @OneToOne
//    @JoinColumn(name = "material_id")
//    private Material material;
//
//    @Column(nullable = false)
//    private Double valor;
//
//    @OneToMany(mappedBy = "prestacaoContas", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Documento> anexos = new ArrayList<>();
//
//    public PrestacaoContas() {
//    }
//
//    public PrestacaoContas(String nome, String documento, String tipoDocumento, LocalDate data, String tipoPrestacao, Material material, Double valor, List<Documento> anexos) {
//        this.nome = nome;
//        this.documento = documento;
//        this.tipoDocumento = tipoDocumento;
//        this.data = data;
//        this.tipoPrestacao = tipoPrestacao;
//        this.material = material;
//        this.valor = valor;
//        this.anexos = anexos;
//    }
//}
