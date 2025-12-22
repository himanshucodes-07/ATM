package com.Atm.Atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;   // Sender Gmail address

    @Value("${email.enabled:true}")
    private boolean emailEnabled;

    /**
     * Send Email - returns true if sent successfully, false if failed.
     */
    public boolean sendEmail(String to, String subject, String message) {

        try {
            // Email feature ON/OFF
            if (!emailEnabled) {
                System.out.println("⚠ Email Service DISABLED — Skipping email.");
                return false;
            }

            // Validate 'to'
            if (to == null || to.trim().isEmpty()) {
                System.out.println("⚠ Email NOT sent — Recipient email is empty!");
                return false;
            }

            // Validate subject
            if (subject == null || subject.trim().isEmpty()) {
                subject = "(No Subject)";
            }

            // Validate message
            if (message == null || message.trim().isEmpty()) {
                message = "(No Message)";
            }

            // Create and send email
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(message);
            mail.setFrom(fromEmail);

            mailSender.send(mail);

            System.out.println("✔ Email sent successfully to " + to);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Email sending FAILED: " + e.getMessage());
            return false;
        }
    }
}
