package com.instakek.api.controller;

import com.instakek.api.dto.request.LoginRequest;
import com.instakek.api.dto.request.SignUpRequest;
import com.instakek.api.dto.response.JwtAuthenticationResponse;
import com.instakek.api.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "authenticateUser", notes = "Takes LoginRequest dto and authenticates user")
    @ApiResponses({
            @ApiResponse(code = 500, message = "User not found"),
            @ApiResponse(code = 200, message = "OK")
    })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("Requesting user authenticating");

        log.debug(loginRequest.toString());

        return ResponseEntity.ok(new JwtAuthenticationResponse(authService.authenticateUser(loginRequest)));
    }

    @ApiOperation(value = "registerUser", notes = "Takes SignUpRequest dto and registers user")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Username already taken || Email Address already in use"),
            @ApiResponse(code = 201, message = "User registered successfully")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.debug("Requesting user registration");

        return authService.registerUser(signUpRequest);
    }

    @ApiOperation(value = "recoverPassword", notes = "Takes email and recovering password via message")
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Password recovered successfully")
    })
    @GetMapping(value = "/recovery")
    @ResponseBody
    public ResponseEntity<?> recoverPassword(@RequestParam("email") String email) {
        log.debug("Requesting password recovering");

        return authService.recoverPassword(email);
    }
}

