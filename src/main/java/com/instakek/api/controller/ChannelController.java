package com.instakek.api.controller;

import com.instakek.api.dto.request.SubscriptionRequest;
import com.instakek.api.model.Channel;
import com.instakek.api.security.CurrentUser;
import com.instakek.api.security.UserPrincipal;
import com.instakek.api.service.ChannelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channel")
@Slf4j
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @ApiOperation(value = "getAllChannels", notes = "Returns a list of all channels")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping
    public ResponseEntity<?> getAllChannels() {
        log.debug("Requesting all channels");

        return ResponseEntity.ok(channelService.getAll());
    }

    @ApiOperation(value = "getChannelById", notes = "Get channel by it's id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getChannelById(@PathVariable long id) {
        log.debug("Requesting channel(id = {})", id);

        return ResponseEntity.ok(channelService.getChannelWithPostsAndSubscribers(id));
    }

    @ApiOperation(value = "insertChannel", notes = "Inserts new channel. Returns inserted channel with id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> insertChannel(@CurrentUser UserPrincipal user, @RequestBody Channel channel) {
        log.debug("Inserting new channel");

        channel.setCreatorId(user.getId());

        return ResponseEntity.ok(channelService.create(channel));
    }

    @ApiOperation(value = "updateChannel", notes = "Updates existing channel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Channel with exact id not found")
    })
    @PutMapping
    public ResponseEntity<?> updateChannel(@RequestBody Channel channel) {
        log.debug("Updating channel(id = {})", channel.getId());

        channelService.update(channel);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "deleteChannel", notes = "Updates existing channel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Channel does not belong to current user"),
            @ApiResponse(code = 500, message = "Channel with exact id not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChannel(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Deleting channel(id = {})", id);

        if (channelService.getById(id) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (channelService.getById(id).getCreatorId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        channelService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "subscribeUserToChannel", notes = "Subscribes current user to a channel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/subscription/{id}")
    public ResponseEntity<?> subscribeUserToChannel(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Subscribing user(id = {}) to channel(id = {})", user.getId(), id);

        channelService.subscribe(user.getId(), id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "unsubscribeUserFromChannel", notes = "Unsubscribes current user from a channel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/subscription/{id}")
    public ResponseEntity<?> unsubscribeUserFromChannel(@CurrentUser UserPrincipal user, @PathVariable long id) {
        log.debug("Subscribing user(id = {}) to channel(id = {})", user.getId(), id);

        channelService.unsubscribe(user.getId(), id);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "approveUserSubscriptionToChannel", notes = "Approves some user subscribtion to a channel")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/subscription")
    public ResponseEntity<?> approveUserSubscriptionToChannel(@RequestBody SubscriptionRequest subscriptionRequest) {
        log.debug("Subscribing user(id = {}) to channel(id = {})",
                subscriptionRequest.getUserId(), subscriptionRequest.getChannelId());

        channelService.approveSubscription(subscriptionRequest.getUserId(), subscriptionRequest.getChannelId());

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "getChannelsContainingName",
            notes = "Returns a list of channels titles of which contain requested text")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/search/{text}")
    public ResponseEntity<?> getChannelsContainingName(@PathVariable String text) {
        log.debug("Requesting all channels containing '{}' in their title", text);

        return ResponseEntity.ok(channelService.getChannelsContainingName(text));
    }
}

