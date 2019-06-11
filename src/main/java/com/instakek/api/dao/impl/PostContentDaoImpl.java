package com.instakek.api.dao.impl;

import com.instakek.api.dao.PostContentDao;
import com.instakek.api.mapper.PostContentMapper;
import com.instakek.api.model.PostContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class PostContentDaoImpl extends GenericDaoImpl<PostContent> implements PostContentDao {

    @Value("${db.query.postContent.insert}")
    private String sqlPostContentInsert;

    @Value("${db.query.postContent.update}")
    private String sqlPostContentUpdate;

    public PostContentDaoImpl() {
        super(new PostContentMapper(), TableName.POST_CONTENT);
    }

    @Override
    protected String getInsertSql() {
        return sqlPostContentInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, PostContent entity) throws SQLException {

        int argNum = 1;
        statement.setLong(argNum++, entity.getPostId());
        statement.setString(argNum++, entity.getContentLink());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlPostContentUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(PostContent entity) {
        return new Object[]{entity.getPostId(), entity.getContentLink()};
    }
}
