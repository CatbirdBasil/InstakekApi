package com.instakek.api.service;

import com.instakek.api.dao.PostContentDao;
import com.instakek.api.dao.PostDao;
import com.instakek.api.model.Post;
import com.instakek.api.model.PostContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class PostService extends CrudService<Post> {

    private PostDao postDao;
    private PostContentDao postContentDao;

    @Autowired
    public PostService(PostDao postDao, PostContentDao postContentDao) {
        super(postDao, "Post");
        this.postDao = postDao;
        this.postContentDao = postContentDao;
    }

    public Post getPostWithContents(long postId) {
        Post post = super.getById(postId);
        post.setContents(postContentDao.getPostsContents(postId));
        return post;
    }

    public Post getPostWithComments(long postId) {
        Post post = super.getById(postId);
        post.setComments();
        return post;
    }

    public List<Post> getSubscribedPosts(long userId) {
        List<Post> posts = postDao.getPostsFromSubscribedChannels(userId);
        posts.addAll(postDao.getPostsFromSubscribedTags(userId));

        posts.sort((Post post1, Post post2) -> post2.getCreationDate().compareTo(post1.getCreationDate()));

        for (int i = 0; i < posts.size(); i++) {
            posts.get(i).setContents(postContentDao.getPostsContents(posts.get(i).getId()));
        }

        return posts;
    }

    @Override
    public Post create(Post post) {
        Post createdPost = super.create(post);
        List<PostContent> contents = new ArrayList<>();

        for (PostContent content : post.getContents()) {
            content.setPostId(createdPost.getId());
            PostContent createdContent = postContentDao.insert(content);
            contents.add(createdContent);
        }

        createdPost.setContents(contents);
        return createdPost;
    }

    @Override
    public void update(Post post) {
        super.update(post);
        deletePostContents(post.getId());

        for (PostContent content : post.getContents()) {
            content.setPostId(post.getId());
            PostContent createdContent = postContentDao.insert(content);
        }
    }

    @Override
    public void delete(Post post) {
        log.debug("Deleting post(id = {})", post.getId());

        deletePostContents(post.getId());
        postDao.delete(post);
    }

    @Override
    public void deleteById(long postId) {
        log.debug("Deleting post(id = {})", postId);

        deletePostContents(postId);
        postDao.deleteById(postId);
    }

    private void deletePostContents(Long postId) {
        List<PostContent> oldContents = postContentDao.getPostsContents(postId);

        for (PostContent content : oldContents) {
            postContentDao.delete(content);
        }
    }
}
