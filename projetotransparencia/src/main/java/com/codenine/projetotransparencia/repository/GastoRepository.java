package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
}
