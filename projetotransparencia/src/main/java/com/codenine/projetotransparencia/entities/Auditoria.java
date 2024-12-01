package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoria_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(nullable = false)
    private String tipoAuditoria;

    @Column(nullable = false)
    private String nomeCoordenador;

    @Column(nullable = true)
    private String titulo_antigo;

    @ManyToOne
    @JoinColumn(name = "parceiro_antigo_id", nullable = true)
    private Parceiro parceiro_antigo;

    @Column(nullable = true)
    private String descricao_antiga;

    @Column(nullable = true)
    private Double valor_antigo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate dataInicio_antiga;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate dataTermino_antiga;

    @Column(nullable = true)
    private String status_antigo;

    @Column(nullable = true)
    private String integrantes_antigos;

    @Column(nullable = true)
    private String objetivo_antigo;

    @Column(nullable = true)
    private String links_antigos;

    @Column(nullable = true)
    private String titulo_novo;

    @Column(nullable = false)
    private String referenciaProjeto;

    @ManyToOne
    @JoinColumn(name = "parceiro_novo_id", nullable = true)
    private Parceiro parceiro_novo;

    @Column(nullable = true)
    private String descricao_novo;

    @Column(nullable = true)
    private Double valor_novo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate dataInicio_novo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private LocalDate dataTermino_novo;

    @Column(nullable = true)
    private String camposOcultos_novo;

    @Column(nullable = true)
    private String camposOcultos_antigo;

    @Column(nullable = true)
    private String status_novo;

    @Column(nullable = true)
    private String integrantes_novo;

    @Column(nullable = true)
    private String objetivo_novo;

    @Column(nullable = true)
    private String links_novo;

    @OneToMany(mappedBy = "auditoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos_novo;

    @Column(nullable = false)
    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "demanda_antiga_id", nullable = true)
    private ClassificacaoDemanda demanda_antiga;

    @ManyToOne
    @JoinColumn(name = "demanda_nova_id", nullable = true)
    private ClassificacaoDemanda demanda_nova;

    // Campos específicos para Convenio
    @Column(nullable = true)
    private String nomeConvenio_antigo;

    @Column(nullable = true)
    private String cnpjConvenio_antigo;

    @Column(nullable = true)
    private String emailConvenio_antigo;

    @Column(nullable = true)
    private String telefoneConvenio_antigo;

    @Column(nullable = true)
    private String areaColaboracaoConvenio_antigo;

    @Column(nullable = true)
    private String historicoParceriaConvenio_antigo;

    @Column(nullable = true)
    private String nomeConvenio_novo;

    @Column(nullable = true)
    private String cnpjConvenio_novo;

    @Column(nullable = true)
    private String emailConvenio_novo;

    @Column(nullable = true)
    private String telefoneConvenio_novo;

    @Column(nullable = true)
    private String areaColaboracaoConvenio_novo;

    @Column(nullable = true)
    private String historicoParceriaConvenio_novo;

    // Construtores, Getters e Setters (se necessário)

    public Auditoria() {}

    public Auditoria(Projeto projeto, String tipoAuditoria, String nomeCoordenador, String titulo_antigo, Parceiro parceiro_antigo, String descricao_antiga,
                     Double valor_antigo, LocalDate dataInicio_antiga, LocalDate dataTermino_antiga,
                     String status_antigo,String integrantes_antigos, String objetivo_antigo,
                     String links_antigos,String titulo_novo,
                     String referenciaProjeto, Parceiro parceiro_novo, String descricao_novo,
                     Double valor_novo, LocalDate dataInicio_novo, LocalDate dataTermino_novo,
                     String status_novo,String integrantes_novo, String objetivo_novo,
                     String links_novo,LocalDateTime dataAlteracao, String camposOcultos_novo, String camposOcultos_antigo, ClassificacaoDemanda demanda_antiga, ClassificacaoDemanda demanda_nova,
                     String nomeConvenio_antigo, String cnpjConvenio_antigo, String emailConvenio_antigo, String telefoneConvenio_antigo, String areaColaboracaoConvenio_antigo, String historicoParceriaConvenio_antigo,
                     String nomeConvenio_novo, String cnpjConvenio_novo, String emailConvenio_novo, String telefoneConvenio_novo, String areaColaboracaoConvenio_novo, String historicoParceriaConvenio_novo) {
        // Chamar o construtor da classe pai (se necessário)
        this.projeto = projeto;
        this.tipoAuditoria = tipoAuditoria;
        this.nomeCoordenador = nomeCoordenador;
        this.titulo_antigo = titulo_antigo;
        this.parceiro_antigo = parceiro_antigo;
        this.descricao_antiga = descricao_antiga;
        this.valor_antigo = valor_antigo;
        this.dataInicio_antiga = dataInicio_antiga;
        this.dataTermino_antiga = dataTermino_antiga;
        this.status_antigo = status_antigo;
        this.integrantes_antigos = integrantes_antigos;
        this.objetivo_antigo = objetivo_antigo;
        this.links_antigos = links_antigos;
        this.referenciaProjeto = referenciaProjeto;
        this.parceiro_novo = parceiro_novo;
        this.descricao_novo = descricao_novo;
        this.valor_novo = valor_novo;
        this.dataInicio_novo = dataInicio_novo;
        this.dataTermino_novo = dataTermino_novo;
        this.status_novo = status_novo;
        this.integrantes_novo = integrantes_novo;
        this.objetivo_novo = objetivo_novo;
        this.links_novo = links_novo;
        this.dataAlteracao = dataAlteracao;
        this.camposOcultos_novo = camposOcultos_novo;
        this.camposOcultos_antigo = camposOcultos_antigo;
        this.demanda_antiga = demanda_antiga;
        this.demanda_nova = demanda_nova;

        // Campos Convenio
        this.nomeConvenio_antigo = nomeConvenio_antigo;
        this.cnpjConvenio_antigo = cnpjConvenio_antigo;
        this.emailConvenio_antigo = emailConvenio_antigo;
        this.telefoneConvenio_antigo = telefoneConvenio_antigo;
        this.areaColaboracaoConvenio_antigo = areaColaboracaoConvenio_antigo;
        this.historicoParceriaConvenio_antigo = historicoParceriaConvenio_antigo;

        this.nomeConvenio_novo = nomeConvenio_novo;
        this.cnpjConvenio_novo = cnpjConvenio_novo;
        this.emailConvenio_novo = emailConvenio_novo;
        this.telefoneConvenio_novo = telefoneConvenio_novo;
        this.areaColaboracaoConvenio_novo = areaColaboracaoConvenio_novo;
        this.historicoParceriaConvenio_novo = historicoParceriaConvenio_novo;
    }
}
