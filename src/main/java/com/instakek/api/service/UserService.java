package com.instakek.api.service;

import com.instakek.api.dao.UserDao;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.instakek.api.utils.Constants.PasswordCharacters.ALL_PASSWORD_CHARACTERS;

@Service
@Transactional
@Slf4j
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(Long id) {
        log.debug("Getting user");

        return userDao.getById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new AppException("User with id " + id + " not found");
                });
    }

    public boolean existsByUsername(String username) {
        log.debug("Getting exists by username");

        return userDao.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        log.debug("Getting exists by email");

        return userDao.existsByEmail(email);
    }

    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        log.debug("Getting exists by usernameOrEmail");

        return userDao.getUserByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> {
                    log.error("User not found with email or username: {}", usernameOrEmail);
                    return new AppException("User with email or username " + usernameOrEmail + " not found");
                });
    }

    public List<User> getAllUsers() {
        log.debug("Getting users");

        return userDao.getAll();
    }

    public void deleteUserById(long id) {
        log.debug("Deleting user");

        userDao.deleteById(id);
    }

    public void deleteUser(User user) {
        log.debug("Deleting user");

        userDao.delete(user);
    }

    public void updateUser(User user) {
        log.debug("Updating user");

        userDao.update(user);
    }

    public User createUser(User user) {
        log.debug("Creating user");

        return userDao.insert(user);
    }

    public String resetPassword(String email, PasswordEncoder encoder) {
        log.debug("Resetting password for user");

        User user = getUserByUsernameOrEmail(email);
        String newPassword = getNewPassword();
        user.setPassword(encoder.encode(newPassword));
        updateUser(user);
        return newPassword;
    }

    public String getNewPassword() {
        return RandomStringUtils.random(15, ALL_PASSWORD_CHARACTERS);
    }
}
