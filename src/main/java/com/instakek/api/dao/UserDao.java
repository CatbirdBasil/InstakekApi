package com.instakek.api.dao;

import com.instakek.api.model.Channel;
import com.instakek.api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> getUserByUsernameOrEmail(String usernameOrEmail);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> getSubscribersByUserId(Long userId);

    List<Channel> getSubscriptionsByUserId(Long userId);
}
