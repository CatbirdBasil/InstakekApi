package com.instakek.api.service;

import com.instakek.api.dao.ChannelDao;
import com.instakek.api.enums.ChannelType;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.Channel;
import com.instakek.api.model.Post;
import com.instakek.api.model.PostContent;
import com.instakek.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ChannelService extends CrudService<Channel> {

    private ChannelDao channelDao;
    private PostService postService;
    private PostContentService postContentService;

    @Autowired
    public ChannelService(ChannelDao channelDao, PostService postService, PostContentService postContentService) {
        super(channelDao, "Channel");
        this.channelDao = channelDao;
        this.postService = postService;
        this.postContentService = postContentService;
    }

    public Channel createUserBaseChannel(User user) {
        log.debug("Creating user base channel for user (id = " + user.getId() + ")");

        Channel channel = new Channel(user.getId(), ChannelType.USER_PUBLIC, user.getUsername(),
                null, user.getRegistrationDate(), user.getImgSrc());

        return channelDao.insert(channel);
    }

    public Channel getChannelWithPostsAndSubscribers(Long channelId) {
        log.debug("Getting channel (id = {}) with posts and subscribers", channelId);
        Channel channel = getById(channelId);

        List<User> subscribers = channelDao.getChannelSubscribers(channelId);
        List<Post> posts = channelDao.getChannelPosts(channelId);

        for (int i = 0; i < posts.size(); i++) {
            List<PostContent> contents = postContentService.getPostContents(posts.get(i).getId());
            posts.get(i).setContents(contents);
        }

        channel.setPosts(posts);
        channel.setSubscribers(subscribers);

        return channel;
    }

    public void subscribe(Long userId, Long channelId) {
        log.debug("Subscribing user (id = {}) to channel (id = {})", userId, channelId);

        Channel channel = getById(channelId);

        if (channel.getType().equals(ChannelType.USER_PRIVATE) || channel.getType().equals(ChannelType.PRIVATE)) {
            channelDao.subscribePrivate(userId, channelId);
        } else {
            channelDao.subscribePublic(userId, channelId);
        }
    }

    public void unsubscribe(Long userId, Long channelId) {
        log.debug("Unsubscribing user (id = {}) from channel (id = {})", userId, channelId);

        channelDao.unsubscribe(userId, channelId);
    }

    public void approveSubscription(Long userId, Long channelId) {
        log.debug("Approving user (id = {}) for channel (id = {})", userId, channelId);

        channelDao.approveSubscription(userId, channelId);
    }

    public List<Channel> getChannelsContainingName(String name) {
        log.debug("Getting channels containing name = {}", name);

        return channelDao.getChannelsContainingName(name);
    }

    public Channel getBaseChannelByUserId(Long userId) {
        Channel channel = channelDao.getBaseChannelByUserId(userId).orElseThrow(() -> {
            log.error("Channel not found for user with id: {}", userId);
            return new AppException("Channel not found for user with id = " + userId + " not found");
        });

        List<Post> posts = postService.getPostsByUserBaseChannel(userId);
        posts = postService.preparePostsForOutput(posts);

        channel.setPosts(posts);
        return channel;
    }
}
