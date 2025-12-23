package com.Atm.Atm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${email.enabled:true}")
    private boolean emailEnabled;

    /**
     * Send email asynchronously
     */
    @Async
    public boolean sendEmail(String to, String subject, String message) {

        if (!emailEnabled) {
            log.warn("Email service disabled. Skipping email.");
            return false;
        }

        if (to == null || to.trim().isEmpty()) {
            log.warn("Email not sent. Recipient is empty.");
            return false;
        }

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(fromEmail);
            mail.setTo(to);
            mail.setSubject(subject == null || subject.isBlank() ? "(No Subject)" : subject);
            mail.setText(message == null || message.isBlank() ? "(No Message)" : message);

            mailSender.send(mail);

            log.info("Email sent successfully to {}", to);
            return true;

        } catch (Exception ex) {
            log.error("Email sending failed", ex);
            return false;
        }
    }
}
