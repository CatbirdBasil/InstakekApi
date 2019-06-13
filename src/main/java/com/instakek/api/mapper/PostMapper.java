package com.instakek.api.mapper;

import com.instakek.api.model.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.PostColumns;

public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        Post post = new Post();

        post.setId(resultSet.getLong(PostColumns.POST_ID));
        post.setText(resultSet.getString(PostColumns.TEXT));
        post.setChannelId(resultSet.getLong(PostColumns.CHANNEL_ID));
        post.setCreationDate(resultSet.getTimestamp(PostColumns.CREATION_TIME));
        post.setBasePostId(resultSet.getLong(PostColumns.BASE_POST_ID));

        return post;
    }
}
