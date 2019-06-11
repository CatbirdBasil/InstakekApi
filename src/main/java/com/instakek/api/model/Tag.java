package com.instakek.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends Identified {

    //changed from 'text' to 'tagText'
    private String tagText;
}
