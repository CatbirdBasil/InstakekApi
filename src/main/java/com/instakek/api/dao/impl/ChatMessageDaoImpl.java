package com.instakek.api.dao.impl;

import com.instakek.api.dao.ChatMessageDao;
import com.instakek.api.mapper.ChatMessageMapper;
import com.instakek.api.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.instakek.api.utils.Constants.TableName;

@Repository
@Slf4j
public class ChatMessageDaoImpl extends GenericDaoImpl<ChatMessage> implements ChatMessageDao {

    @Value("${db.query.chatMessage.insert}")
    private String sqlChatMessageInsert;

    @Value("${db.query.chatMessage.update}")
    private String sqlChatMessageUpdate;

    public ChatMessageDaoImpl() {
        super(new ChatMessageMapper(), TableName.CHAT_MESSAGE);
    }

    @Override
    protected String getInsertSql() {
        return sqlChatMessageInsert;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(PreparedStatement statement, ChatMessage entity) throws SQLException {

        int argNum = 1;
        statement.setLong(argNum++, entity.getSenderId());
        statement.setLong(argNum++, entity.getReceiverId());
        statement.setString(argNum++, entity.getMessageText());
        statement.setTimestamp(argNum++, entity.getMessageTime());

        return statement;
    }

    @Override
    protected String getUpdateSql() {
        return sqlChatMessageUpdate;
    }

    @Override
    protected Object[] getArgsForUpdate(ChatMessage entity) {
        return new Object[]{entity.getSenderId(), entity.getReceiverId(), entity.getMessageText(), entity.getMessageTime()};
    }
}
