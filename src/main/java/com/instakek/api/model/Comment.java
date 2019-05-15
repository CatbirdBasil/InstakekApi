package com.instakek.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Comment extends Identified {

    private long userId;
    private long postId;
    private String text;
    private Timestamp commentTime;

    private User user;
    private Post post;

    public Comment(long userId, long postId, String text, Timestamp commentTime) {
        this.userId = userId;
        this.postId = postId;
        this.text = text;
        this.commentTime = commentTime;
    }
}
