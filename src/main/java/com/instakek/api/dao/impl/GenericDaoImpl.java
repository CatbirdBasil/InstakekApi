package com.instakek.api.dao.impl;

import com.instakek.api.dao.GenericDao;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.Identified;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class GenericDaoImpl<T extends Identified> implements GenericDao<T> {

    private String tableName;
    protected final RowMapper<T> rowMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Value("${db.query.generic.getAll}")
    private String sqlGetAll;

    @Value("${db.query.generic.getById}")
    private String sqlGetUserById;

    @Value("${db.query.generic.deleteById}")
    private String sqlDeleteById;

    public GenericDaoImpl(RowMapper<T> rowMapper, String tableName) {
        this.rowMapper = rowMapper;
        this.tableName = tableName;
    }

    @PostConstruct
    private void initiateSql() {
        sqlGetAll = getSqlWithTableName(sqlGetAll);

        sqlGetUserById = getSqlWithTableName(sqlGetUserById);

        sqlDeleteById = getSqlWithTableName(sqlDeleteById);
    }

    /**
     * Here you should return your table-specified insert query
     *
     * @return Insert query
     */
    protected abstract String getInsertSql();

    /**
     * Fill insert statement with data in order that matches your insert query
     *
     * @return Filled prepared statement
     */
    protected abstract PreparedStatement prepareStatementForInsert(PreparedStatement statement, T entity) throws SQLException;

    /**
     * Here you should return your table-specified update query
     *
     * @return Update query
     */
    protected abstract String getUpdateSql();

    /**
     * Here you should update arguments in order that matches your update query
     *
     * @return Update arguments
     */
    protected abstract Object[] getArgsForUpdate(T entity);

    private String getSqlWithTableName(String sql) {
        return String.format(sql, tableName);
    }

    private Object[] getArgsWithAppendedId(Object[] args, long id) {

        List<Object> objects = new ArrayList<>(Arrays.asList(args));
        objects.add(id);

        return objects.toArray();
    }

    @Override
    public List<T> getAll() {
        log.debug("Getting all elements in {}: ", this.getClass().getName());

        try {
            return jdbcTemplate.query(sqlGetAll, rowMapper);
        } catch (DataAccessException ex) {
            log.error("Error occurred while querying for all entities in {}", this.getClass().getName());
            throw new AppException("Error occurred while querying");
        }
    }

    @Override
    public Optional<T> getById(Long id) {
        log.debug("Getting element by id = {} in {}: ", id, this.getClass().getName());

        try {
            return getSingleElement(jdbcTemplate.query(sqlGetUserById, new Object[]{id}, rowMapper));
        } catch (DataAccessException ex) {
            log.error("Error occurred while querying for entity(id = {}) in {}", id, this.getClass().getName());
            throw new AppException("Error occurred while querying");
        }
    }

    @Override
    public T insert(T entity) {
        log.debug("Inserting element {} in {}: ", entity.toString(), this.getClass().getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            this.jdbcTemplate.update((Connection connection) -> {
                PreparedStatement statement = connection.prepareStatement(getInsertSql(), new String[]{"id"});
                prepareStatementForInsert(statement, entity);
                return statement;
            }, keyHolder);
        } catch (DataAccessException ex) {
            log.error("Error occurred while inserting entity in {}: {}", this.getClass().getName(), entity);
            throw new AppException("Error occurred while inserting: ", ex);
        }

        entity.setId(keyHolder.getKey().longValue());

        return entity;
    }

    @Override
    public void update(T entity) {
        log.debug("Updating element {} in {}: ", entity.toString(), this.getClass().getName());

        try {
            jdbcTemplate.update(getUpdateSql(), getArgsWithAppendedId(getArgsForUpdate(entity), entity.getId()));
        } catch (DataAccessException ex) {
            log.error("Error occurred while updating entity in {}: {}", this.getClass().getName(), entity);
            throw new AppException("Error occurred while updating");
        }
    }

    @Override
    public void delete(T entity) {
        log.debug("Deleting element {} in {} : ", entity.toString(), this.getClass().getName());

        try {
            jdbcTemplate.update(sqlDeleteById, entity.getId());
        } catch (DataAccessException ex) {
            log.error("Error occurred while deleting entity in {}: {}", this.getClass().getName(), entity);
            throw new AppException("Error occurred while deleting");
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting element by id = {} in {}: ", id, this.getClass().getName());

        try {
            jdbcTemplate.update(sqlDeleteById, id);
        } catch (DataAccessException ex) {
            log.error("Error occurred while deleting entity(id = {}) in {}", id, this.getClass().getName());
            throw new AppException("Error occurred while deleting");
        }
    }

    @Override
    public Optional<T> getSingleElement(List<T> element) {

        if (element.isEmpty()) {
            return Optional.empty();
        } else if (element.size() == 1) {
            return Optional.of(element.get(0));
        } else {
            throw new IncorrectResultSizeDataAccessException(1);
        }
    }

    @Override
    public Optional<Long> getCountElement(List<Long> element) {
        if (element.isEmpty()) {
            return Optional.empty();
        } else if (element.size() == 1) {
            return Optional.of(element.get(0));
        } else {
            throw new IncorrectResultSizeDataAccessException(1);
        }
    }
}
