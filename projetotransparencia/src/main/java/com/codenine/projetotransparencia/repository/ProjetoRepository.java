package com.codenine.projetotransparencia.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codenine.projetotransparencia.entities.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>, ProjetoRepositoryCustom {
    @Query("SELECT p FROM Projeto p LEFT JOIN p.parceiro parceiro WHERE " +
            "(:referencia IS NULL OR LOWER(p.referencia) LIKE LOWER(CONCAT('%', :referencia, '%'))) AND " +
            "((:keyword IS NOT NULL AND (" +
            "LOWER(p.referencia) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.titulo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.descricao) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.integrantes) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.links) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.objeto) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(parceiro.nome) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.nomeCoordenador) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")) OR :keyword IS NULL) AND " +
            "(:nomeCoordenador IS NULL OR LOWER(p.nomeCoordenador) LIKE LOWER(CONCAT('%', :nomeCoordenador, '%'))) AND " +
            "(:dataInicio IS NULL OR p.dataInicio >= :dataInicio) AND " +
            "(:dataTermino IS NULL OR p.dataTermino <= :dataTermino) AND " +
            "(:valor IS NULL OR p.valor = :valor) AND " +
            "(:status IS NULL OR LOWER(p.status) LIKE LOWER(CONCAT('%', :status, '%'))) AND " +
            "p.ativo = true " +
            "ORDER BY p.dataInicio ASC")
    List<Projeto> findByFiltros(@Param("referencia") String referencia,
                                @Param("nomeCoordenador") String nomeCoordenador,
                                @Param("dataInicio") LocalDate dataInicio,
                                @Param("dataTermino") LocalDate dataTermino,
                                @Param("valor") Double valor,
                                @Param("status") String status,
                                @Param("keyword") String keyword);
    
    List<Projeto> findByReferencia(String referencia);

    long countByNomeCoordenador(String nomeCoordenador);
}
