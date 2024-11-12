package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "classificacao_demanda")
public class ClassificacaoDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classificacao_demanda_id")
    private Long id;

    @Column(nullable = false)
    private String descricao; // Descrição da demanda

    @Column(nullable = false)
    private String statusAtendimento; // Status do atendimento (ex: "Em progresso", "Concluído", "Pendente")

    @Column(nullable = false)
    private String tipo; // Tipo da demanda (ex: "Urgente", "Baixa prioridade")

    @Column(nullable = false)
    private String prioridade; // Prioridade da demanda (ex: "Alta", "Média", "Baixa")

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)  // Associa a chave estrangeira para o Projeto
    private Projeto projeto;  // Relacionamento ManyToOne com Projeto

    // Construtores
    public ClassificacaoDemanda() {
    }

    public ClassificacaoDemanda(String descricao, String statusAtendimento, String tipo, String prioridade) {
        this.descricao = descricao;
        this.statusAtendimento = statusAtendimento;
        this.tipo = tipo;
        this.prioridade = prioridade;
    }
}
