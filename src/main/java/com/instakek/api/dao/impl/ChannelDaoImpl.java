package com.instakek.api.dao.impl;

import com.instakek.api.dao.ChannelDao;
import com.instakek.api.mapper.ChannelMapper;
import com.instakek.api.mapper.PostMapper;
import com.instakek.api.mapper.UserMapper;
import com.instakek.api.model.Channel;
import com.instakek.api.model.Post;
import com.instakek.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class ChannelDaoImpl extends GenericDaoImpl<Channel> implements ChannelDao {

    @Value("${db.query.channel.insert}")
    private String sqlChannelInsert;

    @Value("${db.query.channel.update}")
    private String sqlChannelUpdate;

    @Value("${db.query.channel.subscribePrivate}")
    private String sqlSubscribePrivate;

    @Value("${db.query.channel.subscribePublic}")
    private String sqlSubscribePublic;

    @Value("${db.query.channel.unsubscribe}")
    private String sqlUnsubscribe;

    @Value("${db.query.channel.approveSubscription}")
    private String sqlApproveSubscription;

    @Value("${db.query.channel.getChannelPosts}")
    private String sqlGetChannelPosts;

    @Value("${db.query.channel.getChannelSubscribers}")
    private String sqlChannelSubscribers;

    @Value("${db.query.channel.getChannelsContainingName}")
    private String sqlChannelsContainingName;

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

        Long creatorId = entity.getCreatorId();
        if (creatorId == null) {
            statement.setNull(argNum++, Types.NULL);
        } else {
            statement.setLong(argNum++, creatorId);
        }

        Long channelTypeId = entity.getChannelTypeId();
        if (channelTypeId == null) {
            statement.setNull(argNum++, Types.NULL);
        } else {
            statement.setLong(argNum++, channelTypeId);
        }

        statement.setString(argNum++, entity.getChannelName());
        statement.setString(argNum++, entity.getDescription());
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
        return new Object[]{entity.getCreatorId(), entity.getChannelTypeId(), entity.getChannelName(), entity.getDescription(), entity.getCreationDate(), entity.getImageSrc()};
    }

    @Override
    public void subscribePublic(Long userId, Long channelId) {
        jdbcTemplate.update(sqlSubscribePublic, userId, channelId);
    }

    @Override
    public void subscribePrivate(Long userId, Long channelId) {
        jdbcTemplate.update(sqlSubscribePrivate, userId, channelId);
    }

    @Override
    public void unsubscribe(Long userId, Long channelId) {
        jdbcTemplate.update(sqlUnsubscribe, userId, channelId);
    }

    @Override
    public void approveSubscription(Long userId, Long channelId) {
        jdbcTemplate.update(sqlApproveSubscription, userId, channelId);
    }

    @Override
    public List<Post> getChannelPosts(Long channelId) {
        return jdbcTemplate.query(sqlGetChannelPosts, new Object[]{channelId}, new PostMapper());
    }

    @Override
    public List<User> getChannelSubscribers(Long channelId) {
        return jdbcTemplate.query(sqlChannelSubscribers, new Object[]{channelId}, new UserMapper());
    }

    @Override
    public List<Channel> getChannelsContainingName(String name) {
        return jdbcTemplate.query(sqlChannelsContainingName, new Object[]{name}, rowMapper);
    }
}
