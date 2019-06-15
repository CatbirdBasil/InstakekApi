package com.instakek.api.service;

import com.instakek.api.dao.TagDao;
import com.instakek.api.enums.UserTagType;
import com.instakek.api.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class TagService extends CrudService<Tag> {

    private TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        super(tagDao, "Tag");
        this.tagDao = tagDao;
    }

    public void subscribeTag(long userId, long tagId) {
        log.debug("Subscribing user (id = {}) to tag (id = {})", userId, tagId);
        tagDao.insertUserTag(userId, tagId, UserTagType.SUBSCRIBED.getId());
    }

    public void blockTag(long userId, long tagId) {
        log.debug("Blocking user (id = {}) to tag (id = {})", userId, tagId);
        tagDao.insertUserTag(userId, tagId, UserTagType.BLOCKED.getId());
    }

    public void removeUserTag(long userId, long tagId) {
        log.debug("Removing user (id = {}) subscribed/blocked tag (id = {})", userId, tagId);

    }

    public Tag create(String text) {
        log.debug("Insert tag with text : {}", text);

        return tagDao.getTagByText(text).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setTagText(text);
            return tagDao.insert(tag);
        });
    }

    public void addPostTag(long postId, String text, long userId) {
        tagDao.insertPostTag(postId, create(text).getId(), userId);
    }

    public void removePostTag(long postId, long tagId, long userId) {
        tagDao.removePostTag(postId, tagId, userId);
    }

    public void removePostTagCompletely(long postId, long tagId) {
        tagDao.removePostTagCompletely(postId, tagId);
    }

    public boolean tagExists(String text) {
        log.debug("Does tag (text = {}) exists", text);

        return tagDao.getTagByText(text).isPresent();
    }

    public void createUserTag(Long postId, Long tagId, Long userId) {
// TODO ?????
    }


}
