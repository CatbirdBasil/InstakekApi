package com.instakek.api.mapper;

import com.instakek.api.model.Channel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.ChannelColumns;

public class ChannelMapper implements RowMapper<Channel> {

    @Override
    public Channel mapRow(ResultSet resultSet, int i) throws SQLException {
        Channel channel = new Channel();

        channel.setId(resultSet.getLong(ChannelColumns.CHANNEL_ID));
        channel.setCreatorId(resultSet.getLong(ChannelColumns.CREATOR_ID));
        channel.setChannelTypeId(resultSet.getLong(ChannelColumns.CHANNEL_TYPE_ID));
        channel.setChannelName(resultSet.getString(ChannelColumns.CHANNEL_NAME));
        channel.setCreationDate(resultSet.getTimestamp(ChannelColumns.CREATION_DATE));
        channel.setImageSrc(resultSet.getString(ChannelColumns.IMG_SRC));

        return channel;
    }
}
