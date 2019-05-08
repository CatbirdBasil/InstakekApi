package com.instakek.api.dao;

import com.instakek.api.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> getUserByUsernameOrEmail(String usernameOrEmail);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
