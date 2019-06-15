package com.instakek.api.service;

import com.instakek.api.dao.CommentDao;
import com.instakek.api.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CommentService extends CrudService<Comment> {

    private CommentDao commentDao;

    @Autowired
    public CommentService(CommentDao commentDao) {
        super(commentDao, "Comment");
        this.commentDao = commentDao;
    }

    public List<Comment> getPostComments(long postId) {
        return commentDao.getPostComments(postId);
    }
}
