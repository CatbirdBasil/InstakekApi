package com.instakek.api.model;

import com.instakek.api.enums.UserTagType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTag {

    private long userId;
    private long tagId;
    private long tagTypeId;

    private User user;
    private Tag tag;
    private UserTagType type;

    public UserTag(long userId, long tagId, long tagTypeId) {
        this.userId = userId;
        this.tagId = tagId;
        this.tagTypeId = tagTypeId;
        this.type = UserTagType.valueOf(UserTagType.getNameFromId(tagTypeId));
    }

    public UserTag(long userId, long tagId, UserTagType type) {
        this.userId = userId;
        this.tagId = tagId;
        this.type = type;
        this.tagTypeId = type.getId();
    }
}
