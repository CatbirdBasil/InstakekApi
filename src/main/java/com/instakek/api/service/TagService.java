package com.instakek.api.service;

import com.instakek.api.dao.TagDao;
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

    //TODO with enum
    public void subscribeTag(Long userId, Long tagId) {
        log.debug("Subscribing user (id = {}) to tag (id = {})", userId, tagId);
        tagDao.insertUserTag(userId, tagId, Long.valueOf(1));
    }

    public void blockTag(Long userId, Long tagId) {
        log.debug("Blocking user (id = {}) to tag (id = {})", userId, tagId);
        tagDao.insertUserTag(userId, tagId, Long.valueOf(2));
    }

    public void create(String text) {
        log.debug("Insert tag with text : ", text);
        Tag tag = new Tag();
        tag.setTagText(text);
        tagDao.insert(tag);
        tagDao.insert(tag);
    }

    public void createUserTag(Long postId, Long tagId, Long userId) {

    }


//    public Tag getTagById(Long id) {
//        log.debug("Getting tag");
//
//        return tagDao.getById(id)
//                .orElseThrow(() -> {
//                    log.error("Tag not found with id: {}", id);
//                    return new AppException("Tag with id " + id + " not found");
//                });
//    }
//
//    public List<Tag> getAllTags() {
//        log.debug("Getting tags");
//
//        return tagDao.getAll();
//    }
//
//    public void deleteTagById(long id) {
//        log.debug("Deleting tag");
//
//        tagDao.deleteById(id);
//    }
//
//    public void deleteTag(Tag tag) {
//        log.debug("Deleting tag");
//
//        tagDao.delete(tag);
//    }
//
//    public void updateTag(Tag tag) {
//        log.debug("Updating tag");
//
//        tagDao.update(tag);
//    }
//
//    public Tag createTag(Tag tag) {
//        log.debug("Creating tag");
//
//        return tagDao.insert(tag);
//    }
}
