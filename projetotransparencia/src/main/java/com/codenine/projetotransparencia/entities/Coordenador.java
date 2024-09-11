package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordenador_id")
    private Long coordenadorId;

    @Column(nullable = false)
    private String coordenadorNome;

    @Column(length = 11, nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;
}
