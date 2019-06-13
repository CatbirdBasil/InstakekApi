package com.instakek.api.dao;

import com.instakek.api.model.Tag;

public interface TagDao extends GenericDao<Tag> {

    void insertUserTag(Long userId, Long tagId, Long tag_type);


    // TODO: 27.05.2019 Specific methods
}
