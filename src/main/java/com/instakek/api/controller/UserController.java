package com.instakek.api.controller;

import com.instakek.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "getAllUsers", notes = "Returns a list of all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        log.debug("Requesting all users");

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiOperation(value = "getSubscribersByUserId", notes = "Returns a list of user subscribers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/subscribers/{id}")
    public ResponseEntity<?> getSubscribersByUserId(@PathVariable Long id) {
        log.debug("Requesting a list of user(id = {}) subscribers", id);

        return ResponseEntity.ok(userService.getSubscribersByUserId(id));
    }

    @ApiOperation(value = "getSubscriptionsByUserId", notes = "Returns a list of user subscriptions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable Long id) {
        log.debug("Requesting a list of user(id = {}) subscriptions", id);

        return ResponseEntity.ok(userService.getSubscriptionsByUserId(id));
    }
}
