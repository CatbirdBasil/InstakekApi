package com.instakek.api.service;

import com.instakek.api.dto.request.LoginRequest;
import com.instakek.api.dto.request.SignUpRequest;
import com.instakek.api.exception.AppException;
import com.instakek.api.model.User;
import com.instakek.api.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import static com.instakek.api.utils.Constants.ResponseEntities;

@Service
@Transactional
@Slf4j
public class AuthService {

    private ChannelService channelService;
    private UserService userService;
    private EmailService emailService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthService(ChannelService channelService, UserService userService, EmailService emailService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.channelService = channelService;
        this.userService = userService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public ResponseEntity registerUser(SignUpRequest request) {

        log.debug("Registering user");

        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntities.BAD_REQ_USERNAME_TAKEN;
        }
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntities.BAD_REQ_EMAIL_TAKEN;
        }

        User user = new User(request.getEmail(), request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(), request.getSurname(),
                //Timestamp.from(Instant.now()), false);
                Timestamp.from(Instant.now()), true);


        User createdUser = userService.createUser(user);

        channelService.createUserBaseChannel(createdUser);

        return ResponseEntities.USER_REGISTERED_SUCCESSFULLY;
    }

    public String authenticateUser(LoginRequest request) {

        log.debug("Authenticating user");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (userService.getUserByUsernameOrEmail(request.getUsernameOrEmail()).isActive()) {
            return tokenProvider.generateToken(authentication);
        } else {
            throw new AppException("Account is not activated.");
        }
    }

    public ResponseEntity recoverPassword(String email) {

        log.debug("Recovering password for {}", email);

        String newPassword = userService.resetPassword(email, passwordEncoder);

        try {
            emailService.sendEmailForPassRecovery(email, newPassword);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResponseEntities.PASSWORD_RECOVERED_SUCCESSFULLY;
    }
}
