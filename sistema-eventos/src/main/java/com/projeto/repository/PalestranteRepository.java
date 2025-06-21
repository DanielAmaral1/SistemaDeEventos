package com.projeto.repository;

import com.projeto.entities.PalestranteEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PalestranteRepository {

    private EntityManager em;

    public PalestranteRepository(EntityManager em) {
        this.em = em;
    }

    // Salvar palestrante
    public void salvar(PalestranteEntity palestrante) {
        em.getTransaction().begin(); //inicia a transicao
        em.persist(palestrante); // faz o insert da transacao
        em.getTransaction().commit(); // confirma a transacao pelo commit
    }

    //  Buscar todos os palestrantes
    public List<PalestranteEntity> buscarTodos() {
        TypedQuery<PalestranteEntity> query = em.createQuery("SELECT p FROM PalestranteEntity p", PalestranteEntity.class);
        return query.getResultList();
    }

    //  Buscar palestrante por ID
    public PalestranteEntity buscarPorId(Long id) {
        return em.find(PalestranteEntity.class, id);
    }

    //  Atualizar palestrante
    public void atualizar(PalestranteEntity palestrante) {
        em.getTransaction().begin();
        em.merge(palestrante);
        em.getTransaction().commit();
    }

    //  Remover palestrante
    public void remover(PalestranteEntity palestrante) {
        em.getTransaction().begin();
        if (!em.contains(palestrante)) {
            palestrante = em.merge(palestrante);
        }
        em.remove(palestrante);
        em.getTransaction().commit();
    }

    //  Buscar palestrantes por especialidade (exemplo de método adicional)
    public List<PalestranteEntity> buscarPorEspecialidade(String especialidade) {
        TypedQuery<PalestranteEntity> query = em.createQuery(
            "SELECT p FROM PalestranteEntity p WHERE p.especialidade_palestrante = :especialidade", PalestranteEntity.class
        );
        query.setParameter("especialidade", especialidade);
        return query.getResultList();
    }
    
    public List<PalestranteEntity> buscarPorIntervaloDeIdade(int idadeMin, int idadeMax) {
        TypedQuery<PalestranteEntity> query = em.createQuery(
            "SELECT p FROM PalestranteEntity p WHERE p.idade_palestrante BETWEEN :idadeMin AND :idadeMax", PalestranteEntity.class
        );
        query.setParameter("idadeMin", idadeMin);
        query.setParameter("idadeMax", idadeMax);
        return query.getResultList();
    }
}