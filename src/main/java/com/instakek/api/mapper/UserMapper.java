package com.instakek.api.mapper;

import com.instakek.api.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.UserColumns;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong(UserColumns.USER_ID));
        user.setUsername(resultSet.getString(UserColumns.USERNAME));
        user.setEmail(resultSet.getString(UserColumns.EMAIL));
        user.setPassword(resultSet.getString(UserColumns.PASSWORD));
        user.setName(resultSet.getString(UserColumns.NAME));
        user.setSurname(resultSet.getString(UserColumns.SURNAME));
        user.setRegistrationDate(resultSet.getTimestamp(UserColumns.REGISTRATION_DATE));
        user.setImgSrc(resultSet.getString(UserColumns.IMAGE_SRC));
        user.setActive(resultSet.getBoolean(UserColumns.IS_ACTIVE));

        return user;
    }
}
