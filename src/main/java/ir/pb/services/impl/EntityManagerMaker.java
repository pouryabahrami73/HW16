package ir.pb.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EntityManagerMaker {
    private static EntityManager entityManager;
    private EntityManagerMaker() {
    }
    public synchronized static EntityManager getEntityManager(){
        if(entityManager == null){
            EntityManagerFactory entityManagerFactory =
                    Persistence.createEntityManagerFactory("my-persistence-unit");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }
}
