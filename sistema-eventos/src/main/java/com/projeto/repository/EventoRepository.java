package com.projeto.repository;

import com.projeto.entities.EventoEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EventoRepository {
    private EntityManager em;

    public EventoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(EventoEntity evento) {
        try {
            em.getTransaction().begin();
            em.persist(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void atualizar(EventoEntity evento) {
        try {
            em.getTransaction().begin();
            em.merge(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void remover(EventoEntity evento) {
        try {
            em.getTransaction().begin();
            evento = em.merge(evento);
            em.remove(evento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public EventoEntity buscarPorId(Long id) {
        return em.find(EventoEntity.class, id);
    }

    public List<EventoEntity> buscarTodos() {
        return em.createQuery("SELECT e FROM EventoEntity e", EventoEntity.class).getResultList();
    }

    public List<EventoEntity> buscarPorIntervaloDeDatas(Date dataInicio, Date dataFim) {
        String jpql = "SELECT e FROM EventoEntity e WHERE e.data_evento BETWEEN :dataInicio AND :dataFim";
        return em.createQuery(jpql, EventoEntity.class)
                .setParameter("dataInicio", dataInicio)
                .setParameter("dataFim", dataFim)
                .getResultList();
    }
    
    public List<EventoEntity> buscarPorIntervaloDeDuracao(int duracaoMin, int duracaoMax) {
        String jpql = "SELECT e FROM EventoEntity e WHERE e.duracao_evento BETWEEN :duracaoMin AND :duracaoMax";
        return em.createQuery(jpql, EventoEntity.class)
                .setParameter("duracaoMin", duracaoMin)
                .setParameter("duracaoMax", duracaoMax)
                .getResultList();
    }
    
    // JOIN entre Evento e Palestrante
    public List<Object[]> buscarEventosComPalestrantes() {
        String jpql = "SELECT e.nome_evento, e.duracao_evento, e.data_evento, p.nome, p.especialidade_palestrante " +
                      "FROM EventoEntity e " +
                      "JOIN e.palestrante p " +
                      "ORDER BY e.data_evento";
        return em.createQuery(jpql, Object[].class).getResultList();
    }
    
    public Map<String, Object> obterEstatisticas() {
        Map<String, Object> estatisticas = new HashMap<>();
        
        // Total de eventos
        Query queryTotalEventos = em.createQuery("SELECT COUNT(e) FROM EventoEntity e");
        Long totalEventos = (Long) queryTotalEventos.getSingleResult();
        estatisticas.put("totalEventos", totalEventos);
        
        // Duração média dos eventos
        Query queryDuracaoMedia = em.createQuery("SELECT AVG(e.duracao_evento) FROM EventoEntity e");
        Double duracaoMedia = (Double) queryDuracaoMedia.getSingleResult();
        estatisticas.put("duracaoMedia", duracaoMedia != null ? duracaoMedia : 0);
        
        // Evento mais longo
        Query queryEventoMaisLongo = em.createQuery(
            "SELECT e.nome_evento, e.duracao_evento FROM EventoEntity e " +
            "WHERE e.duracao_evento = (SELECT MAX(e2.duracao_evento) FROM EventoEntity e2)"
        );
        Object[] eventoMaisLongo = (Object[]) queryEventoMaisLongo.getResultList().stream().findFirst().orElse(null);
        estatisticas.put("eventoMaisLongo", eventoMaisLongo);
        
        // Palestrante com mais eventos
        Query queryPalestranteMaisEventos = em.createQuery(
            "SELECT p.nome, COUNT(e) as total FROM EventoEntity e " +
            "JOIN e.palestrante p " +
            "GROUP BY p.id_palestrante, p.nome " +
            "ORDER BY total DESC"
        );
        queryPalestranteMaisEventos.setMaxResults(1);
        Object[] palestranteMaisEventos = (Object[]) queryPalestranteMaisEventos.getResultList().stream().findFirst().orElse(null);
        estatisticas.put("palestranteMaisEventos", palestranteMaisEventos);
        
        return estatisticas;
    }
}