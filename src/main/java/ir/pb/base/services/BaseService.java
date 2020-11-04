package ir.pb.base.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseService<E, PK extends Serializable> {

    E save(E e);

    E findById(PK id);

    void deleteById(PK id);

    void deleteAll();

    List<E> findAll();

    List<E> findAllByIdsIn(Collection<PK> ids);

}
