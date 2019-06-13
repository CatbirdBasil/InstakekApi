package com.instakek.api.service;

import com.instakek.api.dao.PostContentDao;
import com.instakek.api.model.PostContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostContentService extends CrudService<PostContent> {

    PostContentDao postContentDao;

    @Autowired
    public PostContentService(PostContentDao postContentDao) {
        super(postContentDao, "PostContent");
        this.postContentDao = postContentDao;
    }

    public List<PostContent> getPostContents(Long postId) {
        return postContentDao.getPostsContents(postId);
    }
}
