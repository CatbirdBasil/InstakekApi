package com.instakek.api.enums;

import lombok.Getter;

@Getter
public enum AdministrationRole {

    ADMINISTRATOR(1, "Administrator"),
    MODERATOR(2, "Moderator");

    private final long id;
    private final String roleName;

    AdministrationRole(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static String getNameFromId(long id) {

        for (AdministrationRole role : values()) {
            if (role.getId() == id) {
                return role.getRoleName();
            }
        }

        return "UNKNOWN";
    }

    public static long getIdFromName(String name) {

        for (AdministrationRole role : values()) {
            if (role.getRoleName().equals(name)) {
                return role.getId();
            }
        }

        return 0;
    }
}

