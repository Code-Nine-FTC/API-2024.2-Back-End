package com.codenine.projetotransparencia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documento_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String caminho;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Long tamanho;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "projeto_id")  // Chave estrangeira para Projeto
    private Projeto projeto;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "auditoria_id") // Chave estrangeira para Auditoria
    private Auditoria auditoria;

    @ManyToOne
    @JoinColumn(name = "gasto_id")  // Adicionando o relacionamento ManyToOne com Gasto
    @JsonIgnore
    private Gasto gasto;

    // Adicionando a propriedade 'receita' para fazer o relacionamento bidirecional
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receita_id") // Chave estrangeira para Receita
    private Receita receita;


    public Documento () {}

//    public Documento (
//            String nome,
//            String caminho,
//            String tipo,
//            Long tamanho,
//            Projeto projeto,
//    ) {
//        this.nome = nome;
//        this.caminho = caminho;
//        this.tipo = tipo;
//        this.tamanho = tamanho;
//        this.projeto = projeto;
//    }

    public Documento(Documento documento) {
        this.id = documento.getId();
        this.nome = documento.getNome();
        this.caminho = documento.getCaminho();
        this.tipo = documento.getTipo();
        this.tamanho = documento.getTamanho();
        this.projeto = documento.getProjeto();
        this.auditoria = documento.getAuditoria();
    }

    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminho='" + caminho + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + tamanho +
                '}';
    }

}


