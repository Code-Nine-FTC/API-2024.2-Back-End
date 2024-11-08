package com.codenine.projetotransparencia.repository;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.codenine.projetotransparencia.entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
public class ProjetoRepositoryCustomImpl implements ProjetoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Projeto> buscarProjetos(String coordenador, String dataInicio, String dataTermino,
                                        String valorMaximo, String valorMinimo,
                                        String situacaoProjeto, String tipoBusca, String contratante) {

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Projeto> query = cb.createQuery(Projeto.class);
            Root<Projeto> root = query.from(Projeto.class);
            List<Predicate> predicates = new ArrayList<>();

            if (coordenador != null && !coordenador.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nomeCoordenador")), "%" + coordenador.toLowerCase() + "%"));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (dataInicio != null && !dataInicio.isEmpty()) {
                LocalDate inicio = LocalDate.parse(dataInicio, formatter);
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataInicio"), java.sql.Date.valueOf(inicio)));
            }

            if (dataTermino != null && !dataTermino.isEmpty()) {
                LocalDate termino = LocalDate.parse(dataTermino, formatter);
                predicates.add(cb.lessThanOrEqualTo(root.get("dataTermino"), java.sql.Date.valueOf(termino)));
            }

            if (valorMaximo != null && !valorMaximo.isEmpty()) {
                try {
                    predicates.add(cb.lessThanOrEqualTo(root.get("valor"), Double.parseDouble(valorMaximo)));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Valor máximo inválido: " + valorMaximo);
                }
            }

            if (valorMinimo != null && !valorMinimo.isEmpty()) {
                try {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("valor"), Double.parseDouble(valorMinimo)));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Valor mínimo inválido: " + valorMinimo);
                }
            }

            if (situacaoProjeto != null && !situacaoProjeto.isEmpty() && !"Todos".equals(situacaoProjeto)) {
                predicates.add(cb.equal(root.get("status"), situacaoProjeto));
            }


            if (tipoBusca != null && !tipoBusca.isEmpty()) {
                predicates.add(cb.equal(root.get("tipoBusca"), tipoBusca));
            }

            if (contratante != null && !contratante.isEmpty()) {
                predicates.add(cb.equal(root.get("contratante"), contratante));
            }

            predicates.add(cb.isTrue(root.get("ativo")));

            query.where(predicates.toArray(new Predicate[0]));
            return entityManager.createQuery(query).getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar projetos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Projeto> findAll() {
        String queryStr = "SELECT p FROM Projeto p WHERE p.ativo = true";
        TypedQuery<Projeto> query = entityManager.createQuery(queryStr, Projeto.class);
        return query.getResultList();
    }
    @Override
    public Optional<Projeto> findActiveById(Long id) {
        String queryStr = "SELECT p FROM Projeto p WHERE p.ativo = true AND p.id = :id";
        TypedQuery<Projeto> query = entityManager.createQuery(queryStr, Projeto.class);
        try {
            Projeto projeto = query.setParameter("id", id).getSingleResult();
            return Optional.of(projeto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
