package com.codenine.projetotransparencia.utils.documents;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class VerificarExcel extends VerificarArquivos {
    @Override
    public boolean verificar(byte[] excel) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(excel)) {
            if (isXLSX(bis) || isXLS(bis)) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private boolean isXLSX(ByteArrayInputStream bis) {
        try {
            WorkbookFactory.create(bis);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isXLS(ByteArrayInputStream bis) {
        try {
            new HSSFWorkbook(bis);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
