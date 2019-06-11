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
    private Timestamp creationDate;
    private String imageSrc;

    private User creator;
    private ChannelType type;

    private List<Post> posts;
    private List<Administration> administrationList;

    public Channel(Long creatorId, Long channelTypeId, String channelName, Timestamp creationDate, String imageSrc) {
        this.creatorId = creatorId;
        this.channelTypeId = channelTypeId;
        this.channelName = channelName;
        this.creationDate = creationDate;
        this.imageSrc = imageSrc;
    }
}
