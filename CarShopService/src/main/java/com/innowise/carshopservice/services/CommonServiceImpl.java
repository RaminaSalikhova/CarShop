package com.innowise.carshopservice.services;

import com.innowise.carshopservice.repositories.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public abstract class CommonServiceImpl<E, R extends CommonRepository<E>> implements CommonService<E> {

    protected final R repo;

    @PersistenceContext
    private EntityManager entityManger;

    @Autowired
    public CommonServiceImpl(R repo) {
        this.repo = repo;
    }

    @Override
    public E findById(Long id) {
        Optional<E> entity = repo.findById(id);
        return entity.orElse(null);
    }

    @Override
    public List<E> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional
    public E save(E entity) {
         entityManger.persist(entity);
         return entity;
    }

    @Override
    @Transactional
    public E update(E entity) {
        return entityManger.merge(entity);
    }

}
