package com.instakek.api.dao.impl;

import com.instakek.api.dao.ChannelDao;
import com.instakek.api.mapper.ChannelMapper;
import com.instakek.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class ChannelDaoImpl extends GenericDaoImpl<Channel> implements ChannelDao {

    @Value("${db.query.channel.insert}")
    private String sqlChannelInsert;

    @Value("${db.query.channel.update}")
    private String sqlChannelUpdate;

    public ChannelDaoImpl() {
        super(new ChannelMapper(), TableName.CHANNEL);
    }

    @Override
    protected String getInsertSql() {
        return sqlChannelInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, Channel entity) throws SQLException {

        int argNum = 1;
        statement.setLong(argNum++, entity.getCreatorId());
        statement.setLong(argNum++, entity.getChannelTypeId());
        statement.setString(argNum++, entity.getChannelName());
        statement.setTimestamp(argNum++, entity.getCreationDate());
        statement.setString(argNum++, entity.getImageSrc());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlChannelUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(Channel entity) {
        return new Object[]{entity.getCreatorId(), entity.getChannelTypeId(), entity.getChannelName(), entity.getCreationDate(), entity.getImageSrc()};
    }
}
