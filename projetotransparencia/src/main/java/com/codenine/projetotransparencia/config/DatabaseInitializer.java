// config/DatabaseInitializer.java
package com.codenine.projetotransparencia.config;

import com.codenine.projetotransparencia.entities.Administrador;
import com.codenine.projetotransparencia.services.AdministradorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(AdministradorService administradorService) {
        return args -> {
            // Defina o administrador fixo com e-mail e senha predefinidos
            String emailFixo = "administradorAfpg@gmail.com";
            String senhaFixa = "1234";
            
            // Verifica se já existe um administrador com o e-mail fixo
            if (administradorService.findByEmail(emailFixo) == null) {
                Administrador administrador = new Administrador();
                administrador.setEmail(emailFixo);
                administrador.setSenha(senhaFixa);
                administradorService.createAdministrador(administrador);
                System.out.println("Administrador padrão criado com sucesso.");
            } else {
                System.out.println("Administrador padrão já existe.");
            }
        };
    }
}
