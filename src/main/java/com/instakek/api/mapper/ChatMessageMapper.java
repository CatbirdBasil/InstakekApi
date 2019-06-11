package com.instakek.api.mapper;

import com.instakek.api.model.ChatMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.ChatMessageColumns;

public class ChatMessageMapper implements RowMapper<ChatMessage> {

    @Override
    public ChatMessage mapRow(ResultSet resultSet, int i) throws SQLException {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setId(resultSet.getLong(ChatMessageColumns.CHAT_MESSAGE_ID));
        chatMessage.setSenderId(resultSet.getLong(ChatMessageColumns.SENDER_ID));
        chatMessage.setReceiverId(resultSet.getLong(ChatMessageColumns.RECIEVER_ID));
        chatMessage.setMessageText(resultSet.getString(ChatMessageColumns.MESSAGE_TEXT));
        chatMessage.setMessageTime(resultSet.getTimestamp(ChatMessageColumns.MESSAGE_TIME));

        return chatMessage;
    }
}
