package com.instakek.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostContent extends Identified {

    private long postId;
    private String contentLink;

    private Post post;

    public PostContent(long postId, String contentLink) {
        this.postId = postId;
        this.contentLink = contentLink;
    }
}
