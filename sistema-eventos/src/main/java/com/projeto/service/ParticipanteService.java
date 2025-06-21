package com.projeto.service;

import java.util.List;
import javax.persistence.EntityManager;

import com.projeto.entities.ParticipanteEntity;
import com.projeto.repository.ParticipanteRepository;
import com.projeto.repository.CustomizerFactory;

public class ParticipanteService {

    //metodos 
    public void salvarParticipante(ParticipanteEntity participante) {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        repository.salvar(participante);
        em.close();
    }

    public List<ParticipanteEntity> buscarTodos() {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        List<ParticipanteEntity> lista = repository.buscarTodos();
        em.close();
        return lista;
    }

    public ParticipanteEntity buscarPorId(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        ParticipanteEntity participante = repository.buscarPorId(id);
        em.close();
        return participante;
    }

    public void atualizarParticipante(ParticipanteEntity participante) {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        repository.atualizar(participante);
        em.close();
    }

    public boolean removerParticipante(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        boolean sucesso = false;

        try {
            em.getTransaction().begin();

            ParticipanteEntity participante = repository.buscarPorId(id);
            if (participante != null) {
                repository.remover(participante);
                em.getTransaction().commit();
                sucesso = true;
            } else {
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }

        return sucesso;
    }

    public List<ParticipanteEntity> buscarPorNomeParcial(String nome) {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        List<ParticipanteEntity> participantes = repository.buscarPorNomeParcial(nome);
        em.close();
        return participantes;
    }
    
    public List<ParticipanteEntity> buscarPorIntervaloDeIdade(int idadeMin, int idadeMax) {
        EntityManager em = CustomizerFactory.getEntityManager();
        ParticipanteRepository repository = new ParticipanteRepository(em);
        List<ParticipanteEntity> participantes = repository.buscarPorIntervaloDeIdade(idadeMin, idadeMax);
        em.close();
        return participantes;
    }
}
