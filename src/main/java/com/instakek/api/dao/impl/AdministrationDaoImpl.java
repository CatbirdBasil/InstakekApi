package com.instakek.api.dao.impl;

import com.instakek.api.dao.AdministrationDao;
import com.instakek.api.mapper.AdministrationMapper;
import com.instakek.api.model.Administration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class AdministrationDaoImpl extends GenericDaoImpl<Administration> implements AdministrationDao {

    @Value("${db.query.administration.insert}")
    private String sqlAdministrationInsert;

    @Value("${db.query.administration.update}")
    private String sqlAdministrationUpdate;

    public AdministrationDaoImpl() {
        super(new AdministrationMapper(), TableName.ADMINISTRATION);
    }

    @Override
    protected String getInsertSql() {
        return sqlAdministrationInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, Administration entity) throws SQLException {

        int argNum = 1;
        statement.setLong(argNum++, entity.getChannelId());
        statement.setLong(argNum++, entity.getUserId());
        statement.setLong(argNum++, entity.getRoleId());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlAdministrationUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(Administration entity) {
        return new Object[]{entity.getChannelId(), entity.getUserId(), entity.getRoleId()};
    }
}
