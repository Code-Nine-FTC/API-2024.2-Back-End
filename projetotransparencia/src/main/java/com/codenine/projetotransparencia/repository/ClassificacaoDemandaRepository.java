package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.ClassificacaoDemanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificacaoDemandaRepository extends JpaRepository<ClassificacaoDemanda, Long> {
}
