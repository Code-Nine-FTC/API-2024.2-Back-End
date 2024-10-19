package com.codenine.projetotransparencia.repository;

import org.springframework.stereotype.Repository;
import com.codenine.projetotransparencia.entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Repository
public class ProjetoRepositoryCustomImpl {

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

            query.where(predicates.toArray(new Predicate[0]));
            return entityManager.createQuery(query).getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar projetos: " + e.getMessage(), e);
        }
    }
}
