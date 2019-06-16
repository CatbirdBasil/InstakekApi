package com.instakek.api.dto.request;

import lombok.Getter;

@Getter
public class SubscriptionRequest {
    private long userId;
    private long channelId;
}
