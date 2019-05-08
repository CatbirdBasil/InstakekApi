package com.instakek.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Identified {

    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Timestamp registrationDate;
    private String imgSrc;
    private boolean isActive;

    public User(String email, String username, String password, String name, String surname, Timestamp registrationDate, boolean isActive) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
    }
}
