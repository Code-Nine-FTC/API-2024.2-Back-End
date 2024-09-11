package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordenador_id")
    private Long coordenadorId;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String coordenadorNome;

    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL)
    private List<Projeto> projetos;
}
