package com.instakek.api.model;

import com.instakek.api.enums.ChannelType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class Channel extends Identified {

    private long creatorId;
    private long channelTypeId;
    private String channelName;
    private String description;
    private Timestamp creationDate;
    private String imageSrc;

    private User creator;
    private ChannelType type;

    private List<Post> posts;
    private List<User> subscribers;
    private List<Administration> administrationList;

    public Channel(Long creatorId, Long channelTypeId, String channelName, String description, Timestamp creationDate, String imageSrc) {
        this.creatorId = creatorId;
        this.channelTypeId = channelTypeId;
        this.type = ChannelType.valueOf(ChannelType.getNameFromId(channelTypeId));
        this.channelName = channelName;
        this.description = description;
        this.creationDate = creationDate;
        this.imageSrc = imageSrc;
    }

    public Channel(Long creatorId, ChannelType type, String channelName, String description, Timestamp creationDate, String imageSrc) {
        this.creatorId = creatorId;
        this.channelTypeId = type.getId();
        this.type = type;
        this.channelName = channelName;
        this.description = description;
        this.creationDate = creationDate;
        this.imageSrc = imageSrc;
    }
}
