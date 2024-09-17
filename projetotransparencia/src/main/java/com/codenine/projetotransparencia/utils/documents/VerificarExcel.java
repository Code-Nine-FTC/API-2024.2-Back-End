package com.codenine.projetotransparencia.utils.documents;

public class VerificarExcel extends VerificarArquivos {
    @Override
    public boolean verificar(byte[] excel) {
        return excel[0] == 0x50 && excel[1] == 0x4B && excel[2] == 0x03 && excel[3] == 0x04;
    }
}
