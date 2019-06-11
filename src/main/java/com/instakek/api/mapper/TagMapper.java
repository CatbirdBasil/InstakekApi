package com.instakek.api.mapper;

import com.instakek.api.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.TagColumns;

public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();

        tag.setId(resultSet.getLong(TagColumns.TAG_ID));
        tag.setTagText(resultSet.getString(TagColumns.TAG_TEXT));

        return tag;
    }
}
