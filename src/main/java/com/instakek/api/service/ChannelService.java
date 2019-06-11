package com.instakek.api.service;

import com.instakek.api.dao.ChannelDao;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ChannelService {

    private ChannelDao channelDao;

    @Autowired
    public ChannelService(ChannelDao channelDao) {
        this.channelDao = channelDao;
    }

    public Channel getChannelById(Long id) {
        log.debug("Getting channel");

        return channelDao.getById(id)
                .orElseThrow(() -> {
                    log.error("Channel not found with id: {}", id);
                    return new AppException("Channel with id " + id + " not found");
                });
    }

    public List<Channel> getAllChannels() {
        log.debug("Getting channels");

        return channelDao.getAll();
    }

    public void deleteChannelById(long id) {
        log.debug("Deleting channel");

        channelDao.deleteById(id);
    }

    public void deleteChannel(Channel channel) {
        log.debug("Deleting channel");

        channelDao.delete(channel);
    }

    public void updateChannel(Channel channel) {
        log.debug("Updating channel");

        channelDao.update(channel);
    }

    public Channel createChannel(Channel channel) {
        log.debug("Creating channel");

        return channelDao.insert(channel);
    }
}
