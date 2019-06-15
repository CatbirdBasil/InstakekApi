package com.instakek.api.dao;

import com.instakek.api.model.Comment;

import java.util.List;

public interface CommentDao extends GenericDao<Comment> {

    List<Comment> getPostComments(long postId);
}
