package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query("SELECT p FROM Projeto p WHERE " +
            "(:referenciaProjeto IS NULL OR p.referenciaProjeto LIKE %:referenciaProjeto%) AND " +
            "(:nomeCoordenador IS NULL OR p.nomeCoordenador LIKE %:nomeCoordenador%) AND " +
            "(:dataInicio IS NULL OR p.dataInicio >= :dataInicio) AND " +
            "(:dataTermino IS NULL OR p.dataTermino <= :dataTermino)")
    List<Projeto> findByFiltros(@Param("referenciaProjeto") String referenciaProjeto,
                                @Param("nomeCoordenador") String nomeCoordenador,
                                @Param("dataInicio") String dataInicio,
                                @Param("dataTermino") String dataTermino);
}

