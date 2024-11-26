package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

}
