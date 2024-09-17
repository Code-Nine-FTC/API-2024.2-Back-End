package com.codenine.projetotransparencia.utils.documents;

public class VerificarPdf extends VerificarArquivos {
    @Override
    public boolean verificar(byte[] pdf) {
        return pdf[0] == 0x25 && pdf[1] == 0x50 && pdf[2] == 0x44 && pdf[3] == 0x46;
    }
}
