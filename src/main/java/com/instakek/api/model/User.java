package com.instakek.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class User extends Identified {

    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Timestamp registrationDate;
    private String imgSrc;
    private boolean isActive;

    private Channel mainChannel;
    private List<Channel> createdChannels;
    private List<Channel> pendingSubscriptions;
    private List<Channel> subscriptions;

    private List<Post> likedPosts;
    private List<Comment> comments;
    private List<UserTag> userTags;

    private List<Administration> administrationList;

    public User(String email, String username, String password, String name, String surname, Timestamp registrationDate, boolean isActive) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
    }
}
