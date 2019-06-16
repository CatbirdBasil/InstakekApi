package com.instakek.api.mapper;

import com.instakek.api.model.Comment;
import com.instakek.api.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.CommentColumns;
import static com.instakek.api.utils.Constants.UserColumns;

public class CommentUserMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();

        comment.setId(resultSet.getLong(CommentColumns.COMMENT_ID));
        comment.setPostId(resultSet.getLong(CommentColumns.POST_ID));
        comment.setUserId(resultSet.getLong(CommentColumns.USER_ID));
        comment.setText(resultSet.getString(CommentColumns.COMMENT_TEXT));
        comment.setCommentTime(resultSet.getTimestamp(CommentColumns.COMMENT_TIME));

        User user = new User();

        user.setId(comment.getUserId());
        user.setUsername(resultSet.getString(UserColumns.USERNAME));
        user.setEmail(resultSet.getString(UserColumns.EMAIL));
        user.setPassword(resultSet.getString(UserColumns.PASSWORD));
        user.setName(resultSet.getString(UserColumns.NAME));
        user.setSurname(resultSet.getString(UserColumns.SURNAME));
        user.setRegistrationDate(resultSet.getTimestamp(UserColumns.REGISTRATION_DATE));
        user.setImgSrc(resultSet.getString(UserColumns.IMAGE_SRC));
        user.setActive(resultSet.getBoolean(UserColumns.IS_ACTIVE));

        comment.setUser(user);

        return comment;
    }
}
