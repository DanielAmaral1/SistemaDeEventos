package com.projeto.repository;

import com.projeto.entities.EventoParticipantesEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EventoParticipantesRepository {

    private EntityManager em;
    // recebe como parametro o Entity Manager e instancia como 'em'
    public EventoParticipantesRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(EventoParticipantesEntity eventoParticipante) {
        em.getTransaction().begin();  // inicia uma transicao no banco
        em.persist(eventoParticipante);  //salva no banco (faz o insert, por exemplo)
        em.getTransaction().commit();  //confirma a trnasicao
    }

    // Consulta com JOIN para buscar eventos e seus participantes
    public List<Object[]> buscarEventosComParticipantes() {
        String jpql = "SELECT e.nome_evento, p.nome " +
                      "FROM EventoParticipantesEntity ep " +
                      "JOIN ep.evento e " +
                      "JOIN ep.participante p";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
    
    // JOIN completo: Evento + Palestrante + Participantes
    public List<Object[]> buscarEventosComPalestrantesEParticipantes() {
        String jpql = "SELECT e.nome_evento, pal.nome, p.nome, e.data_evento " +
                      "FROM EventoParticipantesEntity ep " +
                      "JOIN ep.evento e " +
                      "JOIN e.palestrante pal " +
                      "JOIN ep.participante p " +
                      "ORDER BY e.nome_evento";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
    
    // LEFT JOIN para eventos sem participantes
    public List<Object[]> buscarTodosEventosComOuSemParticipantes() {
        String jpql = "SELECT e.nome_evento, p.nome " +
                      "FROM EventoEntity e " +
                      "LEFT JOIN EventoParticipantesEntity ep ON e.id_evento = ep.evento.id_evento " +
                      "LEFT JOIN ep.participante p " +
                      "ORDER BY e.nome_evento";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
}