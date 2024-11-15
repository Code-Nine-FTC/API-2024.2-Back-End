package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaisRepository extends JpaRepository<Material, Long> {
}
