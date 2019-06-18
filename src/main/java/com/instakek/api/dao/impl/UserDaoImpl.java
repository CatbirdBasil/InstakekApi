package com.instakek.api.dao.impl;

import com.instakek.api.dao.UserDao;
import com.instakek.api.mapper.ChannelMapper;
import com.instakek.api.mapper.UserMapper;
import com.instakek.api.model.Channel;
import com.instakek.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    @Value("${db.query.user.insert}")
    private String sqlUserInsert;

    @Value("${db.query.user.update}")
    private String sqlUserUpdate;

    @Value("${db.query.user.getByUsername}")
    private String sqlGetUserByUsername;

    @Value("${db.query.user.getByEmail}")
    private String sqlGetUserByEmail;

    @Value("${db.query.user.getByUsernameOrEmail}")
    private String sqlGetUserByUsernameOrEmail;

    @Value("${db.query.user.getSubscribersByUserId}")
    private String sqlGetSubscribersByUserId;

    @Value("${db.query.user.getSubscriptionsByUserId}")
    private String sqlGetSubscriptionsByUserId;

    public UserDaoImpl() {
        super(new UserMapper(), TableName.USER);
    }

    @Override
    protected String getInsertSql() {
        return sqlUserInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, User entity) throws SQLException {

        int argNum = 1;
        statement.setString(argNum++, entity.getEmail());
        statement.setString(argNum++, entity.getUsername());
        statement.setString(argNum++, entity.getPassword());
        statement.setString(argNum++, entity.getName());
        statement.setString(argNum++, entity.getSurname());
        statement.setTimestamp(argNum++, entity.getRegistrationDate());
        statement.setString(argNum++, entity.getImgSrc());
        statement.setBoolean(argNum++, entity.isActive());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlUserUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(User entity) {
        return new Object[]{entity.getEmail(), entity.getUsername(), entity.getPassword(), entity.getName(), entity.getSurname(), entity.getRegistrationDate(), entity.getImgSrc(), entity.isActive()};
    }

    @Override
    public Optional<User> getUserByUsernameOrEmail(String usernameOrEmail) {
        log.debug("Getting user by usernameOrEmail: {}", usernameOrEmail);

        List<User> user = jdbcTemplate.query(sqlGetUserByUsernameOrEmail, new Object[]{usernameOrEmail, usernameOrEmail}, rowMapper);

        log.debug("Found streets (by usernameOrEmail) with ids {}", user.stream().map(User::getId).collect(Collectors.toList()));

        return getSingleElement(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return !(jdbcTemplate.query(sqlGetUserByUsername, new Object[]{username}, rowMapper)).isEmpty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return !(jdbcTemplate.query(sqlGetUserByEmail, new Object[]{email}, rowMapper)).isEmpty();
    }

    @Override
    public List<User> getSubscribersByUserId(Long userId) {
        return jdbcTemplate.query(sqlGetSubscribersByUserId, new Object[]{userId}, rowMapper);
    }

    @Override
    public List<Channel> getSubscriptionsByUserId(Long userId) {
        return jdbcTemplate.query(sqlGetSubscriptionsByUserId, new Object[]{userId}, new ChannelMapper());
    }
}
