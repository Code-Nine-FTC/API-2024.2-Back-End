package com.codenine.projetotransparencia.controllers.dto;

import com.codenine.projetotransparencia.entities.Gasto;
import com.codenine.projetotransparencia.entities.Receita;

import java.util.List;

public class SumarioProjetoDto {
    private List<Gasto> gastos;
    private List<Receita> receitas;

    public SumarioProjetoDto(List<Gasto> gastos, List<Receita> receitas) {
        this.gastos = gastos;
        this.receitas = receitas;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos){
        this.gastos = gastos;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }
}
