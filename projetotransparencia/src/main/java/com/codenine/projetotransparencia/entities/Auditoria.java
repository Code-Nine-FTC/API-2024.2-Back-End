package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

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
    private String nomeCoordenador;  // Nome do coordenador que fez a alteração

    @Column(nullable = false)
    private String campoAlterado;  // Nome do campo alterado

    @Column(columnDefinition = "TEXT")
    private String valorAntigo;  // Valor antigo do campo

    @Column(columnDefinition = "TEXT")
    private String valorNovo;  // Valor novo do campo

    private String tipoAcao;  // Tipo de ação: 'EDITAR', 'EXCLUIR'

    private String nomeArquivoOriginal;  // Nome do arquivo original 

    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] arquivoOriginal;  // Versão original do arquivo em caso de modificação/exclusão

    @Column(nullable = false)
    private LocalDateTime dataAlteracao;  // Data e hora da alteração

    public Auditoria() {}

    public Auditoria(Projeto projeto, String nomeCoordenador, String campoAlterado,
                     String valorAntigo, String valorNovo, String tipoAcao,
                     String nomeArquivoOriginal, byte[] arquivoOriginal, LocalDateTime dataAlteracao) {
        this.projeto = projeto;
        this.nomeCoordenador = nomeCoordenador;
        this.campoAlterado = campoAlterado;
        this.valorAntigo = valorAntigo;
        this.valorNovo = valorNovo;
        this.tipoAcao = tipoAcao;
        this.nomeArquivoOriginal = nomeArquivoOriginal;
        this.arquivoOriginal = arquivoOriginal;
        this.dataAlteracao = dataAlteracao;
    }


}
