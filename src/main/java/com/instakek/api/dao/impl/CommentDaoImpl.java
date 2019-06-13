package com.instakek.api.dao.impl;

import com.instakek.api.dao.CommentDao;
import com.instakek.api.mapper.CommentMapper;
import com.instakek.api.model.Comment;
import com.instakek.api.utils.Constants;
import org.springframework.beans.factory.annotation.Value;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CommentDaoImpl extends GenericDaoImpl<Comment> implements CommentDao {

    @Value("${db.query.comment.insert}")
    private String sqlCommentInsert;

    @Value("${db.query.comment.update}")
    private String sqlTagUpdate;

    public CommentDaoImpl() {
        super(new CommentMapper(), Constants.TableName.COMMENT);
    }

    @Override
    protected String getInsertSql() {
        return sqlCommentInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, Comment entity) throws SQLException {

        int argNum = 1;

        Long postId = entity.getPostId();
        if (postId == null) {
            statement.setNull(argNum++, Types.NULL);
        } else {
            statement.setLong(argNum++, postId);
        }

        Long userId = entity.getUserId();
        if (userId == null) {
            statement.setNull(argNum++, Types.NULL);
        } else {
            statement.setLong(argNum++, userId);
        }

        statement.setString(argNum++, entity.getText());
        statement.setTimestamp(argNum++, entity.getCommentTime());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlCommentInsert;
    }

    @Override
    protected Object[] getArgsForUpdate(Comment entity) {
        return new Object[]{entity.getPostId(), entity.getUserId(), entity.getText(), entity.getCommentTime()};
    }
}
