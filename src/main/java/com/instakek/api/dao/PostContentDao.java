package com.instakek.api.dao;

import com.instakek.api.model.PostContent;

import java.util.List;

public interface PostContentDao extends GenericDao<PostContent> {

    List<PostContent> getPostsContents(Long postId);
}
