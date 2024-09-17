package com.codenine.projetotransparencia.utils;


import java.time.Instant;
import java.util.Date;

public class ConversorData {
    public static Date converterIsoParaData(String dataIso) {
        if (dataIso.startsWith("\"") && dataIso.endsWith("\"")) {
            dataIso = dataIso.substring(1, dataIso.length() - 1);
        }
        return Date.from(Instant.parse(dataIso));
    }
}
