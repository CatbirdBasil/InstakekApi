package com.instakek.api.enums;

import lombok.Getter;

@Getter
public enum ChannelType {

    USER_PUBLIC(1, "User Public"),
    USER_PRIVATE(2, "User Private"),
    PUBLIC(3, "Public"),
    PRIVATE(4, "Private");

    private final long id;
    private final String typeName;

    ChannelType(long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public static String getNameFromId(long id) {

        for (ChannelType type : values()) {
            if (type.getId() == id) {
                return type.getTypeName();
            }
        }

        return "UNKNOWN";
    }

    public static long getIdFromName(String name) {

        for (ChannelType type : values()) {
            if (type.getTypeName().equals(name)) {
                return type.getId();
            }
        }

        return 0;
    }
}
