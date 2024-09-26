package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projeto_id")
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String referencia;

    @Column (nullable = true)
    private String contratante;

    @Column(nullable = true)
    private String objeto;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = false)
    private String nomeCoordenador;

    @Column(nullable = true)
    private Double valor;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dataTermino;

//    @Lob
//    @Column(nullable = true, columnDefinition = "LONGBLOB")
//    private byte[] resumoPdf;
//
//    @Lob
//    @Column(nullable = true, columnDefinition = "LONGBLOB")
//    private byte[] resumoExcel;
//
//    @Lob
//    @Column(nullable = true, columnDefinition = "LONGBLOB")
//    private byte[] proposta;
//
//    @Lob
//    @Column(nullable = true, columnDefinition = "LONGBLOB")
//    private byte[] contrato;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    public Projeto() {
    }

    public Projeto(String titulo, String referencia, String contratante, String objeto, String descricao, String nomeCoordenador, Double valor, Date dataInicio, Date dataTermino, byte[] resumoPdf, byte[] resumoExcel, byte[] proposta, byte[] contrato) {
        this.titulo = titulo;
        this.referencia = referencia;
        this.contratante = contratante;
        this.objeto = objeto;
        this.descricao = descricao;
        this.nomeCoordenador = nomeCoordenador;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
//        this.resumoPdf = resumoPdf;
//        this.resumoExcel = resumoExcel;
//        this.proposta = proposta;
//        this.contrato = contrato;
    }
}