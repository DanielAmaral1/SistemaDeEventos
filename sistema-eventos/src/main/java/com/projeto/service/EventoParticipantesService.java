package com.projeto.service;

import com.projeto.entities.EventoParticipantesEntity;
import com.projeto.repository.CustomizerFactory;
import com.projeto.repository.EventoParticipantesRepository;

import javax.persistence.EntityManager;

public class EventoParticipantesService {


    //metodo de salvar os participantes no evento
    public void salvarParticipacao(EventoParticipantesEntity eventoParticipante) {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoParticipantesRepository repository = new EventoParticipantesRepository(em);
        repository.salvar(eventoParticipante);
        em.close();
    }
    
    public java.util.List<Object[]> buscarEventosComParticipantes() {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoParticipantesRepository repository = new EventoParticipantesRepository(em);
        java.util.List<Object[]> resultado = repository.buscarEventosComParticipantes();
        em.close();
        return resultado;
    }
    
    public java.util.List<Object[]> buscarEventosComPalestrantesEParticipantes() {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoParticipantesRepository repository = new EventoParticipantesRepository(em);
        java.util.List<Object[]> resultado = repository.buscarEventosComPalestrantesEParticipantes();
        em.close();
        return resultado;
    }
    
    public java.util.List<Object[]> buscarTodosEventosComOuSemParticipantes() {
        EntityManager em = CustomizerFactory.getEntityManager();
        EventoParticipantesRepository repository = new EventoParticipantesRepository(em);
        java.util.List<Object[]> resultado = repository.buscarTodosEventosComOuSemParticipantes();
        em.close();
        return resultado;
    }
}