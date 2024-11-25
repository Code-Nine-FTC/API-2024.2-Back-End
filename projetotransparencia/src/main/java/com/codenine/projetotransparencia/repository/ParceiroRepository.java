package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {
}