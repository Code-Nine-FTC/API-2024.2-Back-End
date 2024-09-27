package com.codenine.projetotransparencia.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Component
public class ConversorData {
    private static final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date converterIsoParaData(String dataIso) {
        if (dataIso == null || dataIso.isEmpty()) {
            return null;
        }
        try {
            return isoFormat.parse(dataIso);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter data: " + dataIso, e);
        }
    }
}
