package com.instakek.api.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    List<T> getAll();

    Optional<T> getById(Long id);

    T insert(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(Long id);

    Optional<T> getSingleElement(List<T> element);

    Optional<Long> getCountElement(List<Long> element);
}
