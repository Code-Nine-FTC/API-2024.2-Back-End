package com.codenine.projetotransparencia.utils.documents;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class SalvarDocumento {
    public String salvar (MultipartFile arquivo, String caminho) {
        String originalNomeArquivo = arquivo.getOriginalFilename();
        String uniqueNomeArquivo = UUID.randomUUID().toString() + "_" + originalNomeArquivo;
        String caminhoCompleto = caminho + File.separator + uniqueNomeArquivo;
        try {
            File file = new File(caminhoCompleto);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                arquivo.transferTo(file);
                System.out.println("File saved to: " + caminhoCompleto);
            } else {
                System.out.println("File already exists: " + caminhoCompleto);
            }
            return caminhoCompleto;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
