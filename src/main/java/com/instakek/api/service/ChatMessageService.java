package com.instakek.api.service;

import com.instakek.api.dao.ChatMessageDao;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ChatMessageService {

    private ChatMessageDao chatMessageDao;

    @Autowired
    public ChatMessageService(ChatMessageDao chatMessageDao) {
        this.chatMessageDao = chatMessageDao;
    }

    public ChatMessage getChatMessageById(Long id) {
        log.debug("Getting chatMessage");

        return chatMessageDao.getById(id)
                .orElseThrow(() -> {
                    log.error("ChatMessage not found with id: {}", id);
                    return new AppException("ChatMessage with id " + id + " not found");
                });
    }

    public List<ChatMessage> getAllChatMessages() {
        log.debug("Getting chatMessages");

        return chatMessageDao.getAll();
    }

    public void deleteChatMessgeById(long id) {
        log.debug("Deleting chatMessage");

        chatMessageDao.deleteById(id);
    }

    public void deleteChatMessage(ChatMessage chatMessage) {
        log.debug("Deleting chatMessage");

        chatMessageDao.delete(chatMessage);
    }

    public void updateChatMessage(ChatMessage chatMessage) {
        log.debug("Updating chatMessage");

        chatMessageDao.update(chatMessage);
    }

    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        log.debug("Creating chatMessage");

        return chatMessageDao.insert(chatMessage);
    }
}
