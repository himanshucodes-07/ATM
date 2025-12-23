package com.Atm.Atm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${mail.from}")
    private String fromEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean sendEmail(String to, String subject, String content) {

        try {
            String url = "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            String body = """
            {
              "sender": { "email": "%s" },
              "to": [{ "email": "%s" }],
              "subject": "%s",
              "textContent": "%s"
            }
            """.formatted(fromEmail, to, subject, content);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, entity, String.class);

            System.out.println("Email sent via Brevo API");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}



