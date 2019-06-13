package com.instakek.api.dao.impl;

import com.instakek.api.dao.TagDao;
import com.instakek.api.mapper.TagMapper;
import com.instakek.api.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    @Value("${db.query.tag.insertTagForPost}")
    private String sqlInsertPostTag;

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
    public void insertUserTag(Long userId, Long tagId, Long tad_type) {
        // jdbcTemplate.query(sqlInsertUserTag, new Object[]{userId, tagId,tad_type},rowMapper);
        jdbcTemplate.update(sqlInsertUserTag, userId, tagId, tad_type);
    }

    //TODO this
    public void insertPostTag(Tag entity) {

        //jdbcTemplate.update(sqlInsertUserTag, , tagId,tad_type);
    }

}
