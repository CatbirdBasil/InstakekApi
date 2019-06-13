package com.instakek.api.mapper;

import com.instakek.api.model.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.CommentColumns;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();

        comment.setId(resultSet.getLong(CommentColumns.COMMENT_ID));
        comment.setPostId(resultSet.getLong(CommentColumns.POST_ID));
        comment.setUserId(resultSet.getLong(CommentColumns.USER_ID));
        comment.setText(resultSet.getString(CommentColumns.COMMENT_TEXT));
        comment.setCommentTime(resultSet.getTimestamp(CommentColumns.COMMENT_TIME));

        return comment;
    }
}
