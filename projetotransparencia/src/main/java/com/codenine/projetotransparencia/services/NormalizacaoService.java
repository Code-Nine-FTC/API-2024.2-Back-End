package com.codenine.projetotransparencia.services;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Service
public class NormalizacaoService {

    public String normalizarData(String dataString) {
        String dataCorrigida = dataString.replaceAll("[^0-9]", "").trim();

        if (dataCorrigida.length() != 8) {
            throw new IllegalArgumentException("Formato de data inválido: " + dataString);
        }

        String dataFormatada = dataCorrigida.substring(0, 2) + "/" + dataCorrigida.substring(2, 4) + "/" + dataCorrigida.substring(4);

        String[] partes = dataFormatada.split("/");

        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        int ultimoDiaDoMes = getUltimoDiaDoMes(mes, ano);

        if (dia > ultimoDiaDoMes) {
            dia = 1;
            mes++;
            if (mes > 12) {
                mes = 1;
                ano++;
            }
        }

        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }

    private int getUltimoDiaDoMes(int mes, int ano) {
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) ? 29 : 28;
            default:
                return 31;
        }
    }

    public String normalizarValorMonetario(String valorString) {
        if (valorString == null || valorString.trim().isEmpty() || valorString.equals("0")) {
            return "0.00";
        }

        // Remove todos os caracteres não numéricos, exceto vírgulas e pontos
        String valorFormatado = valorString.replaceAll("[^0-9,\\.]", "");

        int indiceUltimaVirgula = valorFormatado.lastIndexOf(',');
        int indiceUltimoPonto = valorFormatado.lastIndexOf('.');

        // Determina o último separador decimal válido (vírgula ou ponto)
        if (indiceUltimaVirgula > indiceUltimoPonto) {
            // Se a última vírgula é o separador decimal, remove todos os pontos e substitui a vírgula por ponto
            valorFormatado = valorFormatado.replace(".", "").replace(",", ".");
        } else if (indiceUltimoPonto > indiceUltimaVirgula) {
            // Se o último ponto é o separador decimal, remove todas as vírgulas
            valorFormatado = valorFormatado.replace(",", "").replaceAll("\\.(?=.*\\.)", "");
        } else {
            // Caso geral: sem múltiplos pontos ou vírgulas, substitui vírgulas por ponto
            valorFormatado = valorFormatado.replace(",", ".");
        }

        try {
            // Converte para double e formata para garantir duas casas decimais
            double valorDecimal = Double.parseDouble(valorFormatado);
            DecimalFormat formatoDecimal = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
            return formatoDecimal.format(valorDecimal);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para normalização: " + valorString, e);
        }
    }
}