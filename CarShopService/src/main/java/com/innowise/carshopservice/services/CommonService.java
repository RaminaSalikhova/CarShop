package com.innowise.carshopservice.services;

import java.util.List;


public interface CommonService<E> {
    List<E> findAll();

    E save(E entity);

    E update(E entity);

    E findById(Long id);
}
