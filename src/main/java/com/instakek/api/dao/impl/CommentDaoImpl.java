package com.instakek.api.dao.impl;

import com.instakek.api.dao.CommentDao;
import com.instakek.api.mapper.CommentMapper;
import com.instakek.api.mapper.CommentUserMapper;
import com.instakek.api.model.Comment;
import com.instakek.api.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
@Slf4j
public class CommentDaoImpl extends GenericDaoImpl<Comment> implements CommentDao {

    @Value("${db.query.comment.insert}")
    private String sqlCommentInsert;

    @Value("${db.query.comment.update}")
    private String sqlTagUpdate;

    @Value("${db.query.comment.getPostComments}")
    private String sqlPostComments;

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

    @Override
    public List<Comment> getPostComments(long postId) {
        return jdbcTemplate.query(sqlPostComments, new Object[]{postId}, new CommentUserMapper());
    }
}
