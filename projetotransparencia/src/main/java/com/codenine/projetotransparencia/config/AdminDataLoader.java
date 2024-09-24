package com.codenine.projetotransparencia.config;

import com.codenine.projetotransparencia.entities.AdministradorV1;
import com.codenine.projetotransparencia.entities.AdminRole;
import com.codenine.projetotransparencia.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataLoader implements CommandLineRunner {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // verificação de existencia de algum adm no banco de dados
        if (administradorRepository.count() == 0) {
            // login de acesso do administrador
            String adminLogin = "administrador@gmail.com";
            String adminPassword = passwordEncoder.encode("adm@@2024Fatec");

            // Criando adm
            AdministradorV1 admin = new AdministradorV1();
            admin.setLogin(adminLogin);
            admin.setPassword(adminPassword);
            admin.setRole(AdminRole.ADMIN);

            // salvando no banco de dados
            administradorRepository.save(admin);

            System.out.println("Administrador criado com sucesso: " + adminLogin);
        } else {
            System.out.println("Administrador já existe. Nenhuma ação necessária.");
        }
    }
}
