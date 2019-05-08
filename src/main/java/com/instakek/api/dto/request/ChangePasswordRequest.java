package com.instakek.api.dto.request;

import com.instakek.api.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotNull
    private User user;

    @NotBlank
    private String oldPassword;
}
