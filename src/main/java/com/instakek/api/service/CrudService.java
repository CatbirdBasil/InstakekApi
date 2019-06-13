package com.instakek.api.service;

import com.instakek.api.dao.GenericDao;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.Identified;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
public abstract class CrudService<T extends Identified> {

    private GenericDao<T> dao;
    private String entityName;

    public CrudService(GenericDao<T> dao, String entityName) {
        this.dao = dao;
        this.entityName = entityName;
    }

    public T getById(Long id) {
        log.debug("Getting {}", entityName);

        return dao.getById(id)
                .orElseThrow(() -> {
                    log.error("{} not found with id: {}", entityName, id);
                    return new AppException(entityName + " with id " + id + " not found");
                });
    }

    public List<T> getAll() {
        log.debug("Getting {}(s)", entityName);

        return dao.getAll();
    }

    public void deleteById(long id) {
        log.debug("Deleting {}", entityName);

        dao.deleteById(id);
    }

    public void delete(T entity) {
        log.debug("Deleting {}", entityName);

        dao.delete(entity);
    }

    public void update(T entity) {
        log.debug("Updating {}", entityName);

        dao.update(entity);
    }

    public T create(T entity) {
        log.debug("Creating {}", entityName);

        return dao.insert(entity);
    }
}
