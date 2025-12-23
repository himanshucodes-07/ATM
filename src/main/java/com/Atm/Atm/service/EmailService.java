package com.Atm.Atm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        var response = restTemplate.postForEntity(url, entity, String.class);

        System.out.println("BREVO STATUS  : " + response.getStatusCode());
        System.out.println("BREVO BODY    : " + response.getBody());

        return response.getStatusCode().is2xxSuccessful();

    } catch (Exception e) {
        System.out.println("BREVO EXCEPTION:");
        e.printStackTrace();
        return false;
    }
}


