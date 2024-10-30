package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoria_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(nullable = false)
    private String tipoAuditoria;

    @Column(nullable = false)
    private String nomeCoordenador;


    @Column(nullable = true)
    private String titulo_antigo;

    @Column(nullable = true)
    private String contratante_antigo;

    @Column(nullable = true)
    private String descricao_antiga;

    @Column(nullable = true)
    private Double valor_antigo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataInicio_antiga;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dataTermino_antiga;

    @Column(nullable = true)
    private String status_antigo;

    @Column(nullable = true)
    private String integrantes_antigos;

    @Column(nullable = true)
    private  String objetivo_antigo;

    @Column(nullable = true)
    private String links_antigos;


    @Column(nullable = true)
    private String titulo_novo;

    @Column(nullable = false)
    private String referenciaProjeto;

    @Column(nullable = true)
    private String contratante_novo;

    @Column(nullable = true)
    private String descricao_novo;

    @Column(nullable = true)
    private Double valor_novo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataInicio_novo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dataTermino_novo;

    @Column(nullable = true)
    private String status_novo;

    @Column(nullable = true)
    private String integrantes_novo;

    @Column(nullable = true)
    private  String objetivo_novo;

    @Column(nullable = true)
    private String links_novo;

    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] arquivoOriginal1;

    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] arquivoOriginal2;

    @Column(nullable = false)
    private LocalDateTime dataAlteracao;

    public Auditoria() {}

    public Auditoria(Projeto projeto, String tipoAuditoria, String nomeCoordenador, String titulo_antigo, String contratante_antigo, String descricao_antiga,
                     Double valor_antigo, Date dataInicio_antiga, Date dataTermino_antiga,
                     String status_antigo,String integrantes_antigos, String objetivo_antigo,
                     String links_antigos,String titulo_novo,
                     String referenciaProjeto, String contratante_novo, String descricao_novo,
                     Double valor_novo, Date dataInicio_novo, Date dataTermino_novo,
                     String status_novo,String integrantes_novo, String objetivo_novo,
                     String links_novo, byte[] arquivoOriginal1, byte[] arquivoOriginal2, LocalDateTime dataAlteracao) {
        this.projeto = projeto;
        this.tipoAuditoria = tipoAuditoria;
        this.nomeCoordenador = nomeCoordenador;
        this.titulo_antigo = titulo_antigo;
        this.contratante_antigo = contratante_antigo;
        this.descricao_antiga = descricao_antiga;
        this.valor_antigo = valor_antigo;
        this.dataInicio_antiga = dataInicio_antiga;
        this.dataTermino_antiga = dataTermino_antiga;
        this.status_antigo = status_antigo;
        this.integrantes_antigos = integrantes_antigos;
        this.objetivo_antigo = objetivo_antigo;
        this.links_antigos = links_antigos;
        this.referenciaProjeto = referenciaProjeto;
        this.contratante_novo = contratante_novo;
        this.descricao_novo = descricao_novo;
        this.valor_novo = valor_novo;
        this.dataInicio_novo = dataInicio_novo;
        this.dataTermino_novo = dataTermino_novo;
        this.status_novo = status_novo;
        this.integrantes_novo = integrantes_novo;
        this.objetivo_novo = objetivo_novo;
        this.links_novo = links_novo;
        this.arquivoOriginal1 = arquivoOriginal1;
        this.arquivoOriginal2 = arquivoOriginal2;
        this.dataAlteracao = dataAlteracao;
    }


}
