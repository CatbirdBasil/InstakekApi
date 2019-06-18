package com.instakek.api.dao.impl;

import com.instakek.api.dao.PostDao;
import com.instakek.api.mapper.PostChannelMapper;
import com.instakek.api.mapper.PostMapper;
import com.instakek.api.mapper.UserMapper;
import com.instakek.api.model.Post;
import com.instakek.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    @Value("${db.query.post.getPostsFromSubscribedChannelsNew}")
    private String sqlGetPostsFromSubscribedChannelsNew;

    @Value("${db.query.post.getPostsFromSubscribedTagsNew}")
    private String sqlGetPostsFromSubscribedTagsNew;

    @Value("${db.query.post.getPostWithChannel}")
    private String sqlGetPostByIdWithChannel;

    @Value("${db.query.post.getPostLikes}")
    private String sqlGetPostLikes;

    @Value("${db.query.post.addLike}")
    private String sqlAddLike;

    @Value("${db.query.post.deleteLike}")
    private String sqlDeleteLike;

    private static RowMapper<Post> postChannelRowMapper = new PostChannelMapper();
    @Value("${db.query.post.getPostsFromUserBaseChannel}")
    private String sqlGetPostsFromUserBaseChannel;

    public PostDaoImpl() {
        super(new PostMapper(), TableName.POST);
    }

    @Override
    protected String getInsertSql() {
        return sqlPostInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, Post entity) throws SQLException {

        Timestamp currTime = Timestamp.from(Instant.now());
        entity.setCreationDate(currTime);

        int argNum = 1;
        statement.setLong(argNum++, entity.getChannelId());
        statement.setString(argNum++, entity.getText());
        statement.setTimestamp(argNum++, currTime);

        Long basePostId = entity.getBasePostId();
        if (basePostId == null) {
            statement.setNull(argNum++, Types.NULL);
        } else {
            statement.setLong(argNum++, basePostId);
        }

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
        return jdbcTemplate.query(sqlGetPostsFromSubscribedChannels, new Object[]{userId}, postChannelRowMapper);
    }

    @Override
    public List<Post> getPostsFromSubscribedChannelsNew(long userId, long lastPostId) {
        return jdbcTemplate.query(sqlGetPostsFromSubscribedChannelsNew, new Object[]{userId, lastPostId}, postChannelRowMapper);
    }

    @Override
    public List<Post> getPostsFromSubscribedTags(long userId) {
        return jdbcTemplate.query(sqlGetPostsFromSubscribedTags, new Object[]{userId}, postChannelRowMapper);
    }

    @Override
    public List<Post> getPostsFromSubscribedTagsNew(long userId, long lastPostId) {
        return jdbcTemplate.query(sqlGetPostsFromSubscribedTagsNew, new Object[]{userId, lastPostId}, postChannelRowMapper);
    }


    @Override
    public Optional<Post> getPostByIdWithChannel(long postId) {
        List<Post> posts = jdbcTemplate.query(sqlGetPostByIdWithChannel, new Object[]{postId}, postChannelRowMapper);
        return getSingleElement(posts);
    }

    @Override
    public List<User> getPostLikes(long postId) {
        return jdbcTemplate.query(sqlGetPostLikes, new Object[]{postId}, new UserMapper());
    }

    @Override
    public void setLike(long postId, long userId) {
        jdbcTemplate.update(sqlAddLike, userId, postId);
    }

    @Override
    public void deleteLike(long postId, long userId) {
        jdbcTemplate.update(sqlDeleteLike, userId, postId);
    }

    @Override
    public List<Post> getPostsFromUserBaseChannel(long userId) {
        return jdbcTemplate.query(sqlGetPostsFromUserBaseChannel, new Object[]{userId}, postChannelRowMapper);
    }
}
