package com.codenine.projetotransparencia.services;

import org.springframework.stereotype.Service;

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
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) ? 29 : 28;
            default:
                return 31;
        }
    }
}
