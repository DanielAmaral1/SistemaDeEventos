package com.projeto.repository;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CustomizerFactory {
    private static final EntityManagerFactory emf;


    static {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        emf = sessionFactory.unwrap(EntityManagerFactory.class);
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void fechar() {
        emf.close();
    }
}
