package com.instakek.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends Identified {

    private String tagText;

    private User postTagCreator;
    private List<User> postTagUsers;
}
