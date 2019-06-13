package com.instakek.api.dao;

import com.instakek.api.model.Post;

import java.util.List;

public interface PostDao extends GenericDao<Post> {

    List<Post> getPostsFromSubscribedChannels(long userId);

    List<Post> getPostsFromSubscribedTags(long userId);
}
