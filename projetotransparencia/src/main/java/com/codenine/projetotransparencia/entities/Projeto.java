package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projeto_id")
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String titulo;

    @Column(nullable = false)
    @NotBlank
    private String referencia;

//    @Column(nullable = true)
//    private String contratante;

    @Column(nullable = true)
    private String objeto;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = false)
    @NotBlank
    private String nomeCoordenador;

    @Column(nullable = true)
    private Double valor;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private LocalDate dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate dataTermino;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private String integrantes;

    @Column(nullable = true)
    private String links;

    @Column(nullable = true)
    private String camposOcultos;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auditoria> auditorias = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parceiro_id", nullable = true)
    private Parceiro parceiro;

    @ManyToOne
    @JoinColumn(name = "classificacao_demanda_id", nullable = true)
    private ClassificacaoDemanda classificacaoDemanda;

    @ManyToMany
    @JoinTable(
            name = "projeto_bolsista",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "bolsista_id")
    )
    private List<Bolsista> bolsistas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "convenio_id", nullable = true)
    private Convenio convenio;

    public Projeto() {
    }

    // Construtor com parâmetros para criação de projeto
    // Adicionando um construtor com 16 parâmetros (não recomendado)
    public Projeto(String titulo, String referencia, String objeto,
                   String descricao, String nomeCoordenador, Double valor,
                   LocalDate dataInicio, LocalDate dataTermino, String status, String integrantes,
                   String links, String camposOcultos, Object o1, Object o2, Object o3, Object o4, Parceiro parceiro, ClassificacaoDemanda classificacaoDemanda, List<Bolsista> bolsistas) {
        this.titulo = titulo;
        this.referencia = referencia;
//        this.contratante = contratante;
        this.objeto = objeto;
        this.descricao = descricao;
        this.nomeCoordenador = nomeCoordenador;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.status = status;
        this.integrantes = integrantes;
        this.links = links;
        this.camposOcultos = camposOcultos;
        this.parceiro = parceiro;
        this.classificacaoDemanda = classificacaoDemanda;
        this.bolsistas = bolsistas;
    }



    public Projeto(Projeto projeto) {
        this.titulo = projeto.titulo;
        this.referencia = projeto.referencia;
//        this.contratante = projeto.contratante;
        this.objeto = projeto.objeto;
        this.descricao = projeto.descricao;
        this.nomeCoordenador = projeto.nomeCoordenador;
        this.valor = projeto.valor;
        this.dataInicio = projeto.dataInicio;
        this.dataTermino = projeto.dataTermino;
        this.status = projeto.status;
        this.integrantes = projeto.integrantes;
        this.links = projeto.links;
        this.camposOcultos = projeto.camposOcultos;
        this.documentos = projeto.documentos.stream()
                .map(Documento::new)
                .collect(Collectors.toList());
        this.parceiro = projeto.parceiro;
        this.classificacaoDemanda = projeto.classificacaoDemanda;
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", referencia='" + referencia + '\'' +
//                ", contratante='" + contratante + '\'' +
                ", objeto='" + objeto + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nomeCoordenador='" + nomeCoordenador + '\'' +
                ", valor=" + valor +
                ", dataInicio=" + dataInicio +
                ", dataTermino=" + dataTermino +
                ", status='" + status + '\'' +
                ", integrantes='" + integrantes + '\'' +
                ", links='" + links + '\'' +
                ", camposOcultos='" + camposOcultos + '\'' +
                ", parceiro='" + parceiro + '\'';
    }
}

