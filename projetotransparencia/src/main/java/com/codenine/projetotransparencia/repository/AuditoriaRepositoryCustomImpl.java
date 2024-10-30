package com.codenine.projetotransparencia.repository;

import com.codenine.projetotransparencia.entities.Auditoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Repository
public class AuditoriaRepositoryCustomImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Auditoria> buscarAuditorias(String tipoAuditoria, String dataInicio, String dataTermino, String resultado, String acao) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Auditoria> query = cb.createQuery(Auditoria.class);
            Root<Auditoria> root = query.from(Auditoria.class);
            List<Predicate> predicates = new ArrayList<>();

            if (tipoAuditoria != null && !tipoAuditoria.isEmpty()) {
                predicates.add(cb.equal(root.get("tipo"), tipoAuditoria));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (dataInicio != null && !dataInicio.isEmpty()) {
                LocalDate inicio = LocalDate.parse(dataInicio, formatter);
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataAuditoria"), java.sql.Date.valueOf(inicio)));
            }

            if (dataTermino != null && !dataTermino.isEmpty()) {
                LocalDate termino = LocalDate.parse(dataTermino, formatter);
                predicates.add(cb.lessThanOrEqualTo(root.get("dataAuditoria"), java.sql.Date.valueOf(termino)));
            }

            if (resultado != null && !resultado.isEmpty()) {
                predicates.add(cb.equal(root.get("resultado"), resultado));
            }

            if (acao != null && !acao.isEmpty()) {
                predicates.add(cb.equal(root.get("acao"), acao)); // Aqui você irá verificar a ação
            }

            query.where(predicates.toArray(new Predicate[0]));
            return entityManager.createQuery(query).getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar auditorias: " + e.getMessage(), e);
        }
    }
}