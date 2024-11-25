package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "parceiro")
public class Parceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parceiro_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String areaColaboracao;

    @Column(nullable = true)
    private String historicoParceria;

    @ManyToMany(mappedBy = "parceiros")
    private List<Projeto> projetos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "parceiro_classificacao_demanda",
            joinColumns = @JoinColumn(name = "parceiro_id"),
            inverseJoinColumns = @JoinColumn(name = "classificacao_demanda_id")
    )
    private List<ClassificacaoDemanda> classificacaoDemandas = new ArrayList<>();

    // Construtores
    public Parceiro() {

    }

    public Parceiro( String nome, String cnpj, String email, String telefone, String areaColaboracao, String historicoParceria) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.areaColaboracao = areaColaboracao;
        this.historicoParceria = historicoParceria;
    }

        public Parceiro(@NotEmpty String nome, @NotEmpty String cnpj, @NotEmpty String email, @NotEmpty String telefone, @NotEmpty String areaColaboracao, Projeto projeto) {
    }
}

