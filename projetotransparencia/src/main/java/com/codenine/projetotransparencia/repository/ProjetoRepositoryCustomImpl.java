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

    // Método para filtrar projetos dinamicamente
    public List<Projeto> filtrarProjetos(String coordenador, String dataInicio, String dataTermino,
                                         String valorMaximo, String valorMinimo,
                                         String situacaoProjeto, String tipoBusca, String contratante) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Projeto> query = cb.createQuery(Projeto.class);
        Root<Projeto> root = query.from(Projeto.class);

        List<Predicate> predicates = new ArrayList<>();

        // Verificar e adicionar condições dinamicamente
        if (coordenador != null && !coordenador.isEmpty()) {
            predicates.add(cb.like(root.get("nomeCoordenador"), "%" + coordenador + "%"));
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
            predicates.add(cb.lessThanOrEqualTo(root.get("valor"), Double.parseDouble(valorMaximo)));
        }

        if (valorMinimo != null && !valorMinimo.isEmpty()) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("valor"), Double.parseDouble(valorMinimo)));
        }

        if (situacaoProjeto != null && !situacaoProjeto.isEmpty()) {
            predicates.add(cb.equal(root.get("status"), situacaoProjeto));
        }

        if (contratante != null && !contratante.isEmpty()) {
            predicates.add(cb.equal(root.get("contratante"), contratante));
        }

        // Adiciona as condições à query
        query.where(predicates.toArray(new Predicate[0]));

        // Executa a query e retorna os resultados
        return entityManager.createQuery(query).getResultList();
    }

    public Long contarProjetosDinamicos(String coordenador, String dataInicio, String dataTermino, 
                                        String valorMaximo, String valorMinimo, 
                                        String situacaoProjeto, String tipoBusca, String contratante) {

        // Criando o CriteriaBuilder e o CriteriaQuery para count
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        // Define o "root" da consulta (a entidade Projeto)
        Root<Projeto> root = query.from(Projeto.class);

        // Seleciona a contagem de resultados
        query.select(cb.count(root));

        // Lista para as condições
        List<Predicate> predicates = new ArrayList<>();

        // Verificar e adicionar cada condição dinamicamente
        if (coordenador != null && !coordenador.isEmpty()) {
            predicates.add(cb.equal(root.get("nomeCoordenador"), coordenador));
        }
       if (dataInicio != null && !dataInicio.isEmpty()) {
            LocalDate inicio = LocalDate.parse(dataInicio); // Certifique-se de que a string esteja em um formato adequado
            predicates.add(cb.greaterThanOrEqualTo(root.get("dataInicio"), inicio));
        }

        if (dataTermino != null && !dataTermino.isEmpty()) {
            LocalDate termino = LocalDate.parse(dataTermino); // Certifique-se de que a string esteja em um formato adequado
            predicates.add(cb.lessThanOrEqualTo(root.get("dataTermino"), termino));
        }
        if (valorMaximo != null && !valorMaximo.isEmpty()) {
            predicates.add(cb.lessThanOrEqualTo(root.get("valor"), Double.parseDouble(valorMaximo)));
        }
        if (valorMinimo != null && !valorMinimo.isEmpty()) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("valor"), Double.parseDouble(valorMinimo)));
        }
        if (situacaoProjeto != null && !situacaoProjeto.isEmpty()) {
            predicates.add(cb.equal(root.get("status"), situacaoProjeto));
        }
        if (tipoBusca != null && !tipoBusca.isEmpty()) {
            predicates.add(cb.equal(root.get("tipoBusca"), tipoBusca));
        }
        if (contratante != null && !contratante.isEmpty()) {
            predicates.add(cb.equal(root.get("contratante"), contratante));
        }

        // Adiciona os predicados na query
        query.where(predicates.toArray(new Predicate[0]));

        // Executar a query e retornar a contagem
        return entityManager.createQuery(query).getSingleResult();
    }

}