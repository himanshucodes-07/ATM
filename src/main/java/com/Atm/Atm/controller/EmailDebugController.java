package com.Atm.Atm.controller;

import com.Atm.Atm.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class EmailDebugController {

    private final EmailService emailService;

    // Constructor Injection (BEST PRACTICE)
    public EmailDebugController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Test email endpoint
     * URL: /debug/email
     */
    @GetMapping("/email")
    public String testEmail() {

        boolean sent = emailService.sendEmail(
                "gothwalhanshu7@gmail.com",
                "Brevo HTTP API Test",
                "If you receive this email, Brevo HTTP email API is working successfully."
        );

        return "Email sent = " + sent;
    }
}
