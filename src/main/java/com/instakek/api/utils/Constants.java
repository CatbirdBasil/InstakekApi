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

    interface ChatMessageColumns {

        String CHAT_MESSAGE_ID = "id";
        String SENDER_ID = "sender_id";
        String RECIEVER_ID = "reciever_id";
        String MESSAGE_TEXT = "message_text";
        String MESSAGE_TIME = "message_time";
    }

    interface TagColumns {

        String TAG_ID = "id";
        String TAG_TEXT = "tag_text";
    }

    interface ChannelColumns {

        String CHANNEL_ID = "id";
        String CREATOR_ID = "creator_id";
        String CHANNEL_TYPE_ID = "channel_type_id";
        String CHANNEL_NAME = "channel_name";
        String CREATION_DATE = "creation_date";
        String IMG_SRC = "img_src";
    }

    interface AdministrationColumns {

        String ADMINISTRATION_ID = "id";
        String CHANNEL_ID = "channel_id";
        String USER_ID = "user_id";
        String ROLE_ID = "role_id";
    }

    interface AdministrationRoleColumns {

        String ADMINISTRATION_ROLE_ID = "id";
        String ROLE_NAME = "role_name";
    }

    interface SubscriptionColumns {

        String CHANNEL_ID = "channel_id";
        String USER_ID = "user_id";
        String IS_ACTIVE = "is_active";
    }

    interface PostColumns {

        String POST_ID = "post_id";
        String CHANNEL_ID = "channel_id";
        String CREATION_TIME = "creation_time";
        String BASE_POST_ID = "base_post_id";
    }

    interface PostContentColumns {

        String POST_CONTENT_ID = "id";
        String POST_ID = "post_id";
        String CONTENT_LINK = "content_link";
    }

    interface EmailNotificationConstants {
        String REG_CONFIRM_TITLE = "Registration Confirmation";
        String PASS_RECOVERY_TITLE = "Password recovery";
        String INSTAKEK_EMAIL = "instakek.team@gmail.com";
        String TEMPLATENAME_REGISTRATION_CONFIRM_EMAIL = "regConfirmTemplate";
        String TEMPLATENAME_PASSWORD_RECOVERY_EMAIL = "passRecoveryTemplate";
    }

    interface CommentColumns{
        String POST_ID = "post_id";
        String USER_ID = "user_id";
        String COMMENT_TEXT = "comment_text";
        String COMMENT_TIME = "comment_time";
    }

    interface ChanellColumns{
        String CREATOR_ID = "creator_id";
        String CHANELL_TYPE_ID = "chanell_type_id";
        String CHANELL_NAME = "chanell_name";
        String CREATION_DATE = "creation_date";
        String IMG_SRC = "img_src";
    }

    interface SubscriptionColumns{
        String USER_ID = "user_id";
        String CHANELL_ID = "chanell_id";
        String IS_ACTIVE = "is_active";
    }

    interface AdministrationColumns{
        String USER_ID = "user_id";
        String CHANELL_ID = "chanell_id";
        String ROLE_ID = "role_id";
    }

    interface PostColumns{
        String CHANELL_ID = "chanell_id";
        String CREATION_DATE = "creation_date";
        String BASE_POST_ID = "base_post_id";
    }



    interface PasswordCharacters {
        String ALL_PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    }

    interface GenericColumnNames {
        String COUNT = "count";
    }
}
