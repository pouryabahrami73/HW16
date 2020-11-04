package ir.pb.base.repositories;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseRepository<E, PK extends Serializable> {

    E save(E e);

    E findById(PK id);
    E findByTitle(String title);

    void deleteByTitle(String title);

    void deleteAll();

    List<E> findAll();
    Object countAll();
    void deleteById(PK id);


    <E, PK extends Serializable> List<E> findAllByIdsIn(Collection<PK> ids);
}
