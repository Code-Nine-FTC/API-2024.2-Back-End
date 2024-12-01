package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "convenio")
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convenio_id")
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

    public Convenio() {
        // Construtor padrão para JPA
    }

    public Convenio(String nome, String cnpj, String email, String telefone, String areaColaboracao, String historicoParceria) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.areaColaboracao = areaColaboracao;
        this.historicoParceria = historicoParceria;
    }
}
