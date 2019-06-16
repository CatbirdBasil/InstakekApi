package com.instakek.api.mapper;


import com.instakek.api.model.Channel;
import com.instakek.api.model.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.ChannelColumns;
import static com.instakek.api.utils.Constants.PostColumns;


public class PostChannelMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        Post post = new Post();

        post.setId(resultSet.getLong(PostColumns.POST_ID));
        post.setText(resultSet.getString(PostColumns.TEXT));
        post.setChannelId(resultSet.getLong(PostColumns.CHANNEL_ID));
        post.setCreationDate(resultSet.getTimestamp(PostColumns.CREATION_TIME));
        post.setBasePostId(resultSet.getLong(PostColumns.BASE_POST_ID));

        Channel channel = new Channel();

        channel.setId(post.getChannelId());
        channel.setCreatorId(resultSet.getLong(ChannelColumns.CREATOR_ID));
        channel.setChannelTypeId(resultSet.getLong(ChannelColumns.CHANNEL_TYPE_ID));
        channel.setChannelName(resultSet.getString(ChannelColumns.CHANNEL_NAME));
        channel.setDescription(resultSet.getString(ChannelColumns.DESCRIPTION));
        channel.setCreationDate(resultSet.getTimestamp(ChannelColumns.CREATION_DATE));
        channel.setImageSrc(resultSet.getString(ChannelColumns.IMG_SRC));

        post.setChannel(channel);

        return post;
    }
}
