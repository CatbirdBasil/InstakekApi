package com.instakek.api.dao.impl;

import com.instakek.api.dao.PostDao;
import com.instakek.api.mapper.PostMapper;
import com.instakek.api.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class PostDaoImpl extends GenericDaoImpl<Post> implements PostDao {

    @Value("${db.query.post.insert}")
    private String sqlPostInsert;

    @Value("${db.query.post.update}")
    private String sqlPostUpdate;

    @Value("${db.query.post.getPostsFromSubscribedChannels}")
    private String sqlGetPostsFromSubscribedChannels;

    @Value("${db.query.post.getPostsFromSubscribedTags}")
    private String sqlGetPostsFromSubscribedTags;

    public PostDaoImpl() {
        super(new PostMapper(), TableName.POST);
    }

    @Override
    protected String getInsertSql() {
        return sqlPostInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, Post entity) throws SQLException {

        int argNum = 1;
        statement.setLong(argNum++, entity.getChannelId());
        statement.setString(argNum++, entity.getText());
        statement.setTimestamp(argNum++, entity.getCreationDate());
        statement.setLong(argNum++, entity.getBasePostId());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlPostUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(Post entity) {
        return new Object[]{entity.getChannelId(), entity.getText(), entity.getCreationDate(), entity.getBasePostId()};
    }

    @Override
    public List<Post> getPostsFromSubscribedChannels(long userId) {
        return jdbcTemplate.query(sqlGetPostsFromSubscribedChannels, new Object[]{userId}, rowMapper);
    }

    @Override
    public List<Post> getPostsFromSubscribedTags(long userId) {
        return jdbcTemplate.query(sqlGetPostsFromSubscribedTags, new Object[]{userId}, rowMapper);
    }
}
