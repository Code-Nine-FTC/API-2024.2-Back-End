package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query("SELECT p FROM Projeto p WHERE " +
            "(:referencia IS NULL OR p.referencia LIKE %:referencia%) AND " +
            "(:nomeCoordenador IS NULL OR p.nomeCoordenador LIKE %:nomeCoordenador%) AND " +
            "(:dataInicio IS NULL OR p.dataInicio >= :dataInicio) AND " +
            "(:dataTermino IS NULL OR p.dataTermino <= :dataTermino) AND " +
            "(:valor IS NULL OR p.valor = :valor) AND" + "(:status IS NULL OR p.status LIKE %:status%)" +
            "ORDER BY p.dataInicio ASC")
    List<Projeto> findByFiltros(@Param("referencia") String referencia,
                                @Param("nomeCoordenador") String nomeCoordenador,
                                @Param("dataInicio") Date dataInicio,
                                @Param("dataTermino") Date dataTermino,
                                @Param("valor") Double valor,
                                @Param("status") String status);

    List<Projeto> findByReferencia(String referencia);

    //   contar projetos pelo nome do coordenador
    long countByNomeCoordenador(String nomeCoordenador);
}
