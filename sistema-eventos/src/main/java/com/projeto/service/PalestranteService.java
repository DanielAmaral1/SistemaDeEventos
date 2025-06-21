package com.projeto.service;

import java.util.List;
import javax.persistence.EntityManager;

import com.projeto.entities.PalestranteEntity;
import com.projeto.repository.PalestranteRepository;
import com.projeto.repository.CustomizerFactory;

public class PalestranteService {


    //metodo de salvar palestrante no banco de dados
    public void salvarPalestrante(PalestranteEntity palestrante) {
        EntityManager em = CustomizerFactory.getEntityManager();
        PalestranteRepository repository = new PalestranteRepository(em);
        repository.salvar(palestrante);
        em.close();
    }
    //metodo de buscar todos os palestrantes no banco de dados
    public List<PalestranteEntity> buscarTodos() {
        EntityManager em = CustomizerFactory.getEntityManager();
        PalestranteRepository repository = new PalestranteRepository(em);
        List<PalestranteEntity> lista = repository.buscarTodos();
        em.close();
        return lista;
    }

    public List<PalestranteEntity> listarPalestrantes() {
        return buscarTodos();
    }

    //metodo para buscar os palestrantes pelo id no banco de dados
    public PalestranteEntity buscarPorId(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        PalestranteRepository repository = new PalestranteRepository(em);
        PalestranteEntity palestrante = repository.buscarPorId(id);
        em.close();
        return palestrante;
    }
    
    public void atualizarPalestrante(PalestranteEntity palestrante) {
        EntityManager em = CustomizerFactory.getEntityManager();
        PalestranteRepository repository = new PalestranteRepository(em);
        repository.atualizar(palestrante);
        em.close();
    }

    public boolean removerPalestrante(Long id) {
        EntityManager em = CustomizerFactory.getEntityManager();
        PalestranteRepository repository = new PalestranteRepository(em);
        PalestranteEntity palestrante = repository.buscarPorId(id);
        if (palestrante != null) {
            repository.remover(palestrante);
            em.close();
            return true;
        }
        em.close();
        return false;
    }
    
    public List<PalestranteEntity> buscarPorIntervaloDeIdade(int idadeMin, int idadeMax) {
        EntityManager em = CustomizerFactory.getEntityManager();
        PalestranteRepository repository = new PalestranteRepository(em);
        List<PalestranteEntity> palestrantes = repository.buscarPorIntervaloDeIdade(idadeMin, idadeMax);
        em.close();
        return palestrantes;
    }
}
