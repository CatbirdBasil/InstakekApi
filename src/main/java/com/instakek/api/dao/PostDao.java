package com.instakek.api.dao;

import com.instakek.api.model.Post;
import com.instakek.api.model.User;

import java.util.List;
import java.util.Optional;

public interface PostDao extends GenericDao<Post> {

    List<Post> getPostsFromSubscribedChannels(long userId);

    List<Post> getPostsFromSubscribedTags(long userId);

    List<Post> getPostsFromSubscribedChannelsNew(long userId, long lastPostId);

    List<Post> getPostsFromSubscribedTagsNew(long userId, long lastPostId);

    Optional<Post> getPostByIdWithChannel(long postId);

    List<User> getPostLikes(long postId);

    void setLike(long postId, long userId);

    void deleteLike(long postId, long userId);

}
