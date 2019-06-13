package com.instakek.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class Post extends Identified {

    private long channelId;
    private String text;
    private Timestamp creationDate;
    private Long basePostId;

    private Channel channel;
    private Post basePost;

    private List<PostContent> contents;
    private List<User> likes;
    private List<Tag> tags;
    private List<Comment> comments;

    public Post(long channelId, String text, Timestamp creationDate, Long basePostId) {
        this.channelId = channelId;
        this.text = text;
        this.creationDate = creationDate;
        this.basePostId = basePostId;
    }
}
