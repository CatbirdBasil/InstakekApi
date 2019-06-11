package com.instakek.api.mapper;

import com.instakek.api.model.Administration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.AdministrationColumns;

public class AdministrationMapper implements RowMapper<Administration> {

    @Override
    public Administration mapRow(ResultSet resultSet, int i) throws SQLException {
        Administration administration = new Administration();

        administration.setId(resultSet.getLong(AdministrationColumns.ADMINISTRATION_ID));
        administration.setChannelId(resultSet.getLong(AdministrationColumns.CHANNEL_ID));
        administration.setUserId(resultSet.getLong(AdministrationColumns.USER_ID));
        administration.setRoleId(resultSet.getLong(AdministrationColumns.ROLE_ID));

        return administration;
    }
}
