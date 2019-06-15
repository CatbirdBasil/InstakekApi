package com.instakek.api.dao.impl;

import com.instakek.api.dao.TagDao;
import com.instakek.api.mapper.TagMapper;
import com.instakek.api.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class TagDaoImpl extends GenericDaoImpl<Tag> implements TagDao {

    @Value("${db.query.tag.insert}")
    private String sqlTagInsert;

    @Value("${db.query.tag.update}")
    private String sqlTagUpdate;

    @Value("${db.query.tag.insertUserTag}")
    private String sqlInsertUserTag;

    @Value("${db.query.tag.removeUserTag}")
    private String sqlRemoveUserTag;

    @Value("${db.query.tag.insertTagForPost}")
    private String sqlInsertPostTag;

    @Value("${db.query.tag.removePostTag}")
    private String sqlRemovePostTag;

    @Value("${db.query.tag.removePostTagCompletely}")
    private String sqlRemovePostTagCompletely;

    @Value("${db.query.tag.getTagByName}")
    private String sqlGetTagByName;

    public TagDaoImpl() {
        super(new TagMapper(), TableName.TAG);
    }

    @Override
    protected String getInsertSql() {
        return sqlTagInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, Tag entity) throws SQLException {

        int argNum = 1;
        statement.setString(argNum++, entity.getTagText());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlTagUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(Tag entity) {
        return new Object[]{entity.getTagText()};
    }

    //TODO проверить инсерт
    @Override
    public void insertUserTag(long userId, long tagId, long tadType) {
        // jdbcTemplate.query(sqlInsertUserTag, new Object[]{userId, tagId,tad_type},rowMapper);
        jdbcTemplate.update(sqlInsertUserTag, userId, tagId, tadType);
    }

    @Override
    public void removeUserTag(long tagId, long userId) {
        jdbcTemplate.update(sqlRemoveUserTag, tagId, userId);
    }

    @Override
    public void insertPostTag(long postId, long tagId, long userId) {
        jdbcTemplate.update(sqlInsertPostTag, postId, tagId, userId);
    }

    @Override
    public void removePostTag(long postId, long tagId, long userId) {
        jdbcTemplate.update(sqlRemovePostTag, postId, tagId, userId);
    }

    @Override
    public void removePostTagCompletely(long postId, long tagId) {
        jdbcTemplate.update(sqlRemovePostTagCompletely, postId, tagId);
    }

    @Override
    public Optional<Tag> getTagByText(String text) {
        return getSingleElement(jdbcTemplate.query(sqlGetTagByName, new Object[]{text}, rowMapper));
    }

}
