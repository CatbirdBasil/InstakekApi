package com.instakek.api.utils;

import com.instakek.api.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface Constants {

    interface ResponseEntities {

        ResponseEntity BAD_REQ_USERNAME_TAKEN = new ResponseEntity<>(new ApiResponse(
                false, "Username is already taken!"
        ), HttpStatus.BAD_REQUEST);

        ResponseEntity BAD_REQ_EMAIL_TAKEN = new ResponseEntity<>(new ApiResponse(
                false, "Email Address already in use!"
        ), HttpStatus.BAD_REQUEST);

        ResponseEntity USER_REGISTERED_SUCCESSFULLY = new ResponseEntity<>(new ApiResponse(
                true, "User registered successfully"
        ), HttpStatus.CREATED);

        ResponseEntity VERIFICATION_TOKEN_EXPIRED = new ResponseEntity<>(new ApiResponse(
                false, "Verification token expired"
        ), HttpStatus.BAD_REQUEST);

        ResponseEntity REGISTRATION_CONFIRMED_SUCCESSFULLY = new ResponseEntity<>(new ApiResponse(
                true, "Illuminati confirmed successfully"
        ), HttpStatus.CREATED);

        ResponseEntity PASSWORD_RECOVERED_SUCCESSFULLY = new ResponseEntity<>(new ApiResponse(
                true, "Password recovered successfully"
        ), HttpStatus.OK);

        ResponseEntity SUBSCRIBED_SUCCESSFULLY = new ResponseEntity<>(new ApiResponse(
                true, "Subscribed successfully"
        ), HttpStatus.CREATED);
    }

    interface TableName {

        String USER = "\"USER\"";
        String CHANNEL = "channel";
        String CHANNEL_TYPE = "channel_type";
        String SUBSCRIPTION = "subscription";
        String ADMINISTRATION = "administration";
        String ADMINISTRATION_ROLE = "administration_role";
        String POST = "post";
        String POST_CONTENT = "post_content";
        String TAG = "tag";
        String POST_TAG = "post_tag";
        String USER_TAG = "user_tag";
        String USER_TAG_TYPE = "user_tag_type";
        String COMMENT = "comment";
        String LIKES = "likes";
        String CHAT_MESSAGE = "chat_message";
    }

    interface UserColumns {

        String USER_ID = "id";
        String DETAILED_USER_ID = "user_id";
        String USERNAME = "username";
        String EMAIL = "email";
        String PASSWORD = "password";
        String NAME = "name";
        String SURNAME = "surname";
        String REGISTRATION_DATE = "registration_date";
        String IMAGE_SRC = "img_src";
        String IS_ACTIVE = "is_active";
    }

    interface EmailNotificationConstants {
        String REG_CONFIRM_TITLE = "Registration Confirmation";
        String PASS_RECOVERY_TITLE = "Password recovery";
        String INSTAKEK_EMAIL = "instakek.team@gmail.com";
        String TEMPLATENAME_REGISTRATION_CONFIRM_EMAIL = "regConfirmTemplate";
        String TEMPLATENAME_PASSWORD_RECOVERY_EMAIL = "passRecoveryTemplate";
    }

    interface PasswordCharacters {
        String ALL_PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    }

    interface GenericColumnNames {
        String COUNT = "count";
    }
}
