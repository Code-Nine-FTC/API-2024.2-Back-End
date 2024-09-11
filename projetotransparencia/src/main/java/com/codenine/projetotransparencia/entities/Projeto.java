package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projeto_id")
    private Long projetoId;

    @ManyToOne
    @JoinColumn(name = "coordenador_id", referencedColumnName = "coordenador_id", nullable = false)
    private Coordenador coordenador;

    @Column(nullable = false)
    private String nomeEmpresa;

    @Column(nullable = false)
    private String objeto;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double valor;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataTermino;
}
