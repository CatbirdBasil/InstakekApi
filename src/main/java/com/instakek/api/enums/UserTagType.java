package com.instakek.api.enums;

import lombok.Getter;

@Getter
public enum UserTagType {

    SUBSCRIBED(1, "Subscribed"),
    BLOCKED(2, "Blocked");

    private final long id;
    private final String typeName;

    UserTagType(long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public static String getNameFromId(long id) {

        for (UserTagType type : values()) {
            if (type.getId() == id) {
                return type.getTypeName();
            }
        }

        return "UNKNOWN";
    }

    public static long getIdFromName(String name) {

        for (UserTagType type : values()) {
            if (type.getTypeName().equals(name)) {
                return type.getId();
            }
        }

        return 0;
    }
}
