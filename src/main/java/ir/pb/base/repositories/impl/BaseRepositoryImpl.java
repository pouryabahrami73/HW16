package ir.pb.base.repositories.impl;



import ir.pb.base.domians.BaseEntity;
import ir.pb.base.repositories.BaseRepository;
import ir.pb.services.impl.EntityManagerMaker;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;



public class BaseRepositoryImpl<E extends BaseEntity<PK>, PK extends Serializable> implements BaseRepository<E, PK> {
    protected final EntityManager entityManager = EntityManagerMaker.getEntityManager();
    private final Class<E> clazz;

    public BaseRepositoryImpl(Class<E> clazz) {
        this.clazz = clazz;
        if (!entityManager.getTransaction().isActive()) {
            this.entityManager.getTransaction().begin();
        }
    }

    @Override
    public E save(E e) {
        try {
            if(!entityManager.contains(e)){
                entityManager.persist(e);
            } else{
                entityManager.merge(e);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public E findById(PK id) {
        E entity = null;
        entity = entityManager.find(clazz, id);
        return entity;
    }


    @Override
    public E findByTitle(String title) {
        TypedQuery<E> query = entityManager.createQuery("SELECT a FROM " + clazz.getName() +
                " a WHERE a.userName =: userName", clazz);
        
        E e = query.setParameter("userName", title).getSingleResult();
        return e;
    }

    @Override
    public void deleteByTitle(String title) {
    }


    @Override
    public void deleteAll() {
        System.out.println("delete by all");
    }

    @Override
    public List<E> findAll() {
        List<E> list;
        TypedQuery<E> findAllQuery = entityManager.createQuery("SELECT a FROM " + clazz.getName() + " a", clazz);
        list = findAllQuery.getResultList();
        return list;
    }

    @Override
    public Object countAll() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM " + clazz.getName());
        Object numberOfRows =  query.getSingleResult();
        return numberOfRows;
    }
    @Override
    public void deleteById(PK id) {

    }

    @Override
    public <E1, PK1 extends Serializable> List<E1> findAllByIdsIn(Collection<PK1> ids) {
        return null;
    }

    public void commitEntityManager(){
        entityManager.getTransaction().commit();
    }
}
