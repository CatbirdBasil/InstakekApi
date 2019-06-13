package com.instakek.api.mapper;

import com.instakek.api.model.PostContent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.PostContentColumns;

public class PostContentMapper implements RowMapper<PostContent> {

    @Override
    public PostContent mapRow(ResultSet resultSet, int i) throws SQLException {
        PostContent postContent = new PostContent();

        postContent.setId(resultSet.getLong(PostContentColumns.POST_CONTENT_ID));
        postContent.setPostId(resultSet.getLong(PostContentColumns.POST_ID));
        postContent.setContentLink(resultSet.getString(PostContentColumns.CONTENT_LINK));

        return postContent;
    }
}
