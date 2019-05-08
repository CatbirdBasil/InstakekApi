package com.instakek.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.instakek.api.utils.Constants.EmailNotificationConstants.*;

@Service
@Slf4j
@Transactional
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Qualifier("templateEngine")
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Async("asyncExecutor")
    public void sendEmailForPassRecovery(String email, String password) throws IOException, MessagingException {
        log.debug("Sending email for password recovery");

        prepareAndSendEmail(email, new HashMap<String, Object>() {
            {
                put("password", password);
            }
        }, PASS_RECOVERY_TITLE, TEMPLATENAME_PASSWORD_RECOVERY_EMAIL);
    }

    private void prepareAndSendEmail(String email, Map<String, Object> values, String title, String templateName) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(values);
        String html = templateEngine.process(templateName, context);

        helper.setTo(email);
        helper.setText(html, true);
        helper.setSubject(title);
        helper.setFrom(INSTAKEK_EMAIL);

        mailSender.send(message);
    }
}
