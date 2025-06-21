package com.projeto.repository;

import com.projeto.entities.ParticipanteEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ParticipanteRepository {

    private EntityManager em;

    public ParticipanteRepository(EntityManager em) {
        this.em = em;
    }

    // Salvar participante
    public void salvar(ParticipanteEntity participante) {
        em.getTransaction().begin();
        em.persist(participante);
        em.getTransaction().commit();
    }

    //  Buscar todos os participantes
    public List<ParticipanteEntity> buscarTodos() {
        TypedQuery<ParticipanteEntity> query = em.createQuery("SELECT p FROM ParticipanteEntity p", ParticipanteEntity.class);
        return query.getResultList();
    }

    //  Buscar participante por ID
    public ParticipanteEntity buscarPorId(Long id) {
        return em.find(ParticipanteEntity.class, id);
    }

    //  Atualizar participante
    public void atualizar(ParticipanteEntity participante) {
        em.getTransaction().begin();
        em.merge(participante);
        em.getTransaction().commit();
    }

    public void remover(ParticipanteEntity participante) {
        em.getTransaction().begin();
        try {
            // Verifica se o participante está gerenciado
            ParticipanteEntity managedParticipante = em.find(ParticipanteEntity.class, participante.getId_participante());
            if (managedParticipante != null) {
                em.remove(managedParticipante); // Remove a entidade gerenciada
            } else {
                System.out.println("Participante não encontrado no banco de dados.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Faz rollback em caso de erro
            throw e; // Relança a exceção para tratamento externo
        }
    }

    // Buscar participantes por número de telefone (exemplo de método adicional)
    public List<ParticipanteEntity> buscarPorNumero(String numero) {
        TypedQuery<ParticipanteEntity> query = em.createQuery(
            "SELECT p FROM ParticipanteEntity p WHERE p.numero_participante = :numero", ParticipanteEntity.class
        );
        query.setParameter("numero", numero);
        return query.getResultList();
    }
    //faz o select para trazer algum nome usando like
    public List<ParticipanteEntity> buscarPorNomeParcial(String nome) {
        String jpql = "SELECT p FROM ParticipanteEntity p WHERE p.nome LIKE :nome";
        TypedQuery<ParticipanteEntity> query = em.createQuery(jpql, ParticipanteEntity.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
    
    public List<ParticipanteEntity> buscarPorIntervaloDeIdade(int idadeMin, int idadeMax) {
        TypedQuery<ParticipanteEntity> query = em.createQuery(
            "SELECT p FROM ParticipanteEntity p WHERE p.idade_participante BETWEEN :idadeMin AND :idadeMax", ParticipanteEntity.class
        );
        query.setParameter("idadeMin", idadeMin);
        query.setParameter("idadeMax", idadeMax);
        return query.getResultList();
    }
}