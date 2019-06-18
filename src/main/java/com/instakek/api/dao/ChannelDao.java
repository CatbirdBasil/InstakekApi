package com.instakek.api.dao;

import com.instakek.api.model.Channel;
import com.instakek.api.model.Post;
import com.instakek.api.model.User;

import java.util.List;
import java.util.Optional;

public interface ChannelDao extends GenericDao<Channel> {

    void subscribePublic(Long userId, Long channelId);

    void subscribePrivate(Long userId, Long channelId);

    void unsubscribe(Long userId, Long channelId);

    void approveSubscription(Long userId, Long channelId);

    List<Post> getChannelPosts(Long channelId);

    List<User> getChannelSubscribers(Long channelId);

    List<Channel> getChannelsContainingName(String name);

    Optional<Channel> getBaseChannelByUserId(Long userId);
}
