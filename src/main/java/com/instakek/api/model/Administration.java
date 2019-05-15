package com.instakek.api.model;

import com.instakek.api.enums.AdministrationRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Administration extends Identified {

    private long channelId;
    private long userId;
    private long roleId;

    private User user;
    private Channel channel;
    private AdministrationRole role;

    public Administration(Long channelId, Long userId, Long roleId) {
        this.channelId = channelId;
        this.userId = userId;
        this.roleId = roleId;
    }
}
