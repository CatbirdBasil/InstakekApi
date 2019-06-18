package com.instakek.api.service;

import com.instakek.api.dao.CommentDao;
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
    private CommentDao commentDao;

    @Autowired
    public PostService(PostDao postDao, PostContentDao postContentDao, CommentDao commentDao) {
        super(postDao, "Post");
        this.postDao = postDao;
        this.postContentDao = postContentDao;
        this.commentDao = commentDao;
    }

    /*public Post getPostForOutput(long postId) {
        Post post = super.getById(postId);
        post.setContents(postContentDao.getPostsContents(postId));
        return post;
    }*/

    public Post getPostWithComments(long postId) {
        log.debug("Getting post(id = {}) with comments", postId);

        Post post = super.getById(postId);
        post.setComments(commentDao.getPostComments(postId));
        return post;
    }

    public List<Post> getSubscribedPosts(long userId) {
        log.debug("Getting subscribed posts for user(id = {})", userId);

        List<Post> posts = postDao.getPostsFromSubscribedChannels(userId);
        posts.addAll(postDao.getPostsFromSubscribedTags(userId));

        return preparePostsForOutput(posts);
    }

    public List<Post> getSubscribedPostsNew(long userId, long lastPostId) {
        log.debug("Getting subscribed posts for user(id = {}) after post(id = {})", userId, lastPostId);

        List<Post> posts = postDao.getPostsFromSubscribedChannelsNew(userId, lastPostId);
        posts.addAll(postDao.getPostsFromSubscribedTagsNew(userId, lastPostId));

        return preparePostsForOutput(posts);
    }

    public List<Post> preparePostsForOutput(List<Post> posts) {
        posts.sort((Post firstPost, Post secondPost) -> secondPost.getCreationDate().compareTo(firstPost.getCreationDate()));

        for (int i = 0; i < posts.size(); i++) {
            posts.get(i).setContents(postContentDao.getPostsContents(posts.get(i).getId()));
            posts.get(i).setLikes(postDao.getPostLikes(posts.get(i).getId()));
        }

        return posts;
    }

    public List<Post> getPostsByUserBaseChannel(long userId) {
        log.debug("Getting posts for user(id = {}) base channel", userId);
        return postDao.getPostsFromUserBaseChannel(userId);
    }

    public void addLike(long userId, long postId) {
        log.debug("Setting like on post(id = {}) by user(id = {})", postId, userId);
        postDao.setLike(postId, userId);
    }

    public void deleteLike(long userId, long postId) {
        log.debug("Deleting like on post(id = {}) by user(id = {})", postId, userId);
        postDao.deleteLike(postId, userId);
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
