package com.instakek.api.dao;

import com.instakek.api.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends GenericDao<Tag> {

    void insertUserTag(long userId, long tagId, long tagType);

    void removeUserTag(long tagId, long userId);

    void insertPostTag(long postId, long tagId, long userId);

    void removePostTag(long postId, long tagId, long userId);

    void removePostTagCompletely(long postId, long tagId);

    Optional<Tag> getTagByText(String text);

    List<Tag> findTagByText(String text);
}
