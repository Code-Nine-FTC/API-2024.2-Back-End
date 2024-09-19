package com.codenine.projetotransparencia.utils.documents;

import org.springframework.stereotype.Component;

@Component
public class VerificarTamanho extends VerificarArquivos {
    @Override
    public boolean verificar(byte[] arquivo) {
        return arquivo.length <= 5242880;
    }
}
