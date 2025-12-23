package com.Atm.Atm.controller;

import com.Atm.Atm.dto.ATMDTO;
import com.Atm.Atm.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
public class ATMController {

    @Autowired
    private ATMService atmService;

    // USER CREATION — PUBLIC
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody ATMDTO dto) {
        Object result = atmService.createUser(dto);

        if (result instanceof String)
            return ResponseEntity.badRequest().body(result);

        return ResponseEntity.ok(result);
    }

    // LOGIN — PUBLIC — RETURNS JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ATMDTO dto) {
        var result = atmService.login(dto);

        if (result == null)
            return ResponseEntity.status(401).body("Invalid Card Number or PIN!");

        return ResponseEntity.ok(result);
    }

    // DEPOSIT — PROTECTED — JWT REQUIRED
   @PostMapping("/deposit")
public ResponseEntity<?> deposit(
        @RequestBody ATMDTO dto,
        @RequestHeader("Authorization") String token
) {
    return ResponseEntity.ok(atmService.deposit(dto, token));
}


    // WITHDRAW — PROTECTED — JWT REQUIRED
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody ATMDTO dto) {
        return ResponseEntity.ok(atmService.withdraw(dto));
    }

    // UPDATE PIN — PROTECTED — JWT REQUIRED
    @PostMapping("/update-pin")
    public ResponseEntity<?> updatePin(@RequestBody ATMDTO dto) {
        return ResponseEntity.ok(atmService.updatePin(dto));
    }

    // DELETE USER — PROTECTED — JWT REQUIRED
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody ATMDTO dto) {
        boolean deleted = atmService.deleteUser(dto.getCardnumber());

        if (!deleted)
            return ResponseEntity.status(404).body("User not found!");

        return ResponseEntity.ok("User deleted successfully!");
    }

    // SEND OTP — PUBLIC
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody ATMDTO dto) {
        if (dto.getEmail() == null)
            return ResponseEntity.badRequest().body("Email is required!");

        return ResponseEntity.ok(atmService.sendOtp(dto.getEmail()));
    }

    // VERIFY OTP — PUBLIC — RETURNS JWT
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody ATMDTO dto) {
        return ResponseEntity.ok(atmService.verifyOtp(dto.getEmail(), dto.getOtp()));
    }
}

