package com.projeto.service;

import com.projeto.entities.EventoEntity;
import com.projeto.repository.EventoRepository;
import com.projeto.repository.CustomizerFactory;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class EventoService {

    public void salvarEvento(EventoEntity evento) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        repository.salvar(evento);
        em.close();
    }

    public List<EventoEntity> buscarTodos() {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        List<EventoEntity> lista = repository.buscarTodos();
        em.close();
        return lista;
    }

    public EventoEntity buscarPorId(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        EventoEntity evento = repository.buscarPorId(id);
        em.close();
        return evento;
    }

    public void atualizarEvento(EventoEntity evento) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        repository.atualizar(evento);
        em.close();
    }

    public boolean removerEvento(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        EventoEntity evento = repository.buscarPorId(id);
        if (evento != null) {
            repository.remover(evento);
            em.close();
            return true;
        }
        em.close();
        return false;
    }

    public List<EventoEntity> buscarPorIntervaloDeDatas(Date dataInicio, Date dataFim) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        List<EventoEntity> eventos = repository.buscarPorIntervaloDeDatas(dataInicio, dataFim);
        em.close();
        return eventos;
    }
    
    public List<EventoEntity> buscarPorIntervaloDeDuracao(int duracaoMin, int duracaoMax) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        List<EventoEntity> eventos = repository.buscarPorIntervaloDeDuracao(duracaoMin, duracaoMax);
        em.close();
        return eventos;
    }
    
    public List<Object[]> buscarEventosComPalestrantes() {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        List<Object[]> resultado = repository.buscarEventosComPalestrantes();
        em.close();
        return resultado;
    }
    
    public Map<String, Object> obterEstatisticas() {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoRepository repository = new EventoRepository(em);
        Map<String, Object> estatisticas = repository.obterEstatisticas();
        em.close();
        return estatisticas;
    }
}