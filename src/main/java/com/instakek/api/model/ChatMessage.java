package com.instakek.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ChatMessage extends Identified {

    private long senderId;
    private long receiverId;
    //changed from 'text' to 'messageText'
    private String messageText;
    private Timestamp messageTime;

    private User sender;
    private User receiver;

    public ChatMessage(long senderId, long receiverId, String messageText, Timestamp messageTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }
}
