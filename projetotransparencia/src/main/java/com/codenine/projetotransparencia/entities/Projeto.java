package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.File;
import java.util.Date;

@Entity
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projeto_id")
    private Long projetoId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String referenciaProjeto;

    @Column (nullable = false)
    private String empresa;

    @Column(nullable = false)
    private String objeto;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = false)
    private String nomeCoordenador;

    @Column(nullable = false)
    private Double valor;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataTermino;

    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] resumoPdf;

    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] resumoExcel;

    public Projeto() {
    }

    public Projeto(String titulo, String referenciaProjeto, String empresa, String objeto, String descricao, String nomeCoordenador, Double valor, Date dataInicio, Date dataTermino, byte[] resumoPdf, byte[] resumoExcel) {
        this.titulo = titulo;
        this.referenciaProjeto = referenciaProjeto;
        this.empresa = empresa;
        this.objeto = objeto;
        this.descricao = descricao;
        this.nomeCoordenador = nomeCoordenador;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.resumoPdf = resumoPdf;
        this.resumoExcel = resumoExcel;
    }
}