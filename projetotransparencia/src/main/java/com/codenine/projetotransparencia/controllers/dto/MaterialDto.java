package com.codenine.projetotransparencia.controllers.dto;
import lombok.Data;

@Data
public class MaterialDto {
    private String nome;
    private Double valor;
    private String fornecedor;
    private String fornecedorEmail;
    private String fornecedorTelefone;
    private String statusUtilizacao;
    private Long gastoId;
}
