package com.instakek.api.controller;

import com.instakek.api.model.User;
import com.instakek.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

        List<User> users = new ArrayList<>();

        User keke = new User("k@k.com", "keke", "passkek",
                "Bobby", "Marley", Timestamp.from(Instant.now()), true);
        User lole = new User("l@k.com", "lole", "passlol",
                "Lobby", "Brachovski", Timestamp.from(Instant.now()), true);

        users.add(keke);
        users.add(lole);

        return ResponseEntity.ok(users);
    }
}
