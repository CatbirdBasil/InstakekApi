package com.instakek.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ChatMessage extends Identified {

    private long senderId;
    private long receiverId;
    private String text;
    private Timestamp messageTime;

    private User sender;
    private User receiver;

    public ChatMessage(long senderId, long receiverId, String text, Timestamp messageTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.messageTime = messageTime;
    }
}
