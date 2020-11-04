package ir.pb.base.services.impl;



import ir.pb.base.repositories.BaseRepository;
import ir.pb.base.services.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class BaseServiceImpl<E, PK extends Serializable, Repository extends BaseRepository<E, PK>> implements BaseService<E, PK> {

    protected Repository baseRepository;

    public BaseServiceImpl(Repository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public E save(E e) {
        return baseRepository.save(e);
    }

    @Override
    public E findById(PK id) {
        return baseRepository.findById(id);
    }

    @Override
    public void deleteById(PK id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        baseRepository.deleteAll();
    }

    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<E> findAllByIdsIn(Collection<PK> ids) {
        return baseRepository.findAllByIdsIn(ids);
    }
}
