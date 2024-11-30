package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    @Column(nullable = true)
    private String nome;

    @Column(nullable = true, unique = true)
    private String cnpj;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String telefone;

    @OneToMany(mappedBy = "parceiro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
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

    public Parceiro(String nome, String cnpj, String email, String telefone) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
    }
}