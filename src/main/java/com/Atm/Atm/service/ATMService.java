package com.Atm.Atm.service;

import com.Atm.Atm.dto.ATMDTO;
import com.Atm.Atm.entity.User;
import com.Atm.Atm.repository.UserRepository;
import com.Atm.Atm.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ATMService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    // Store OTP temporarily
    private Map<String, String> otpStore = new ConcurrentHashMap<>();


    /* ------------------------ DTO Converter ------------------------ */

    private ATMDTO convertToDTO(User user) {
        ATMDTO dto = new ATMDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setCardnumber(user.getCardnumber());
        dto.setTotalamount(user.getTotalamount());
        dto.setRemainingamount(user.getRemainingamount());
        dto.setEmail(user.getEmail());

        // hide sensitive
        dto.setPinnumber(null);
        dto.setLoginCardnumber(null);
        dto.setLoginPin(null);
        dto.setAmount(null);
        dto.setAmountCardnumber(null);

        return dto;
    }


    /* ------------------------ CREATE USER ------------------------ */

    public Object createUser(ATMDTO dto) {

        if (userRepository.existsByCardnumber(dto.getCardnumber()))
            return "Card Number Already Exists!";

        if (userRepository.existsByEmail(dto.getEmail()))
            return "Email Already Registered!";

        User user = new User();
        user.setName(dto.getName());
        user.setCardnumber(dto.getCardnumber());
        user.setPinnumber(dto.getPinnumber());
        user.setEmail(dto.getEmail());
        user.setTotalamount(dto.getTotalamount());
        user.setRemainingamount(dto.getTotalamount()); // starting balance

        userRepository.save(user);

        // email notify
        emailService.sendEmail(
                user.getEmail(),
                "Welcome to ATM System",
                "Hi " + user.getName() +
                        ",\nYour ATM account has been created.\nCard: "
                        + user.getCardnumber() +
                        "\nBalance: ₹" + user.getTotalamount()
        );

        return convertToDTO(user);
    }


    /* ------------------------ LOGIN WITH JWT ------------------------ */

    public Map<String, Object> login(ATMDTO dto) {

        User user = userRepository.findByCardnumberAndPinnumber(
                dto.getLoginCardnumber(),
                dto.getLoginPin()
        );

        if (user == null)
            return null;

        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", convertToDTO(user));

        return map;
    }


    /* ------------------------ SEND OTP ------------------------ */

    public String sendOtp(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            return "Email not registered!";

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        otpStore.put(email, otp);

        emailService.sendEmail(email, "Your ATM Login OTP", "Your OTP: " + otp);

        return "OTP sent to your email!";
    }


    /* ------------------------ VERIFY OTP ------------------------ */

    public Map<String, Object> verifyOtp(String email, String otp) {

        if (!otpStore.containsKey(email))
            return Map.of("error", "OTP not sent!");

        if (!otpStore.get(email).equals(otp))
            return Map.of("error", "Invalid OTP!");

        otpStore.remove(email);

        User user = userRepository.findByEmail(email);

        String token = jwtUtil.generateToken(email);

        return Map.of(
                "message", "OTP Verified!",
                "token", token,
                "user", convertToDTO(user)
        );
    }


    /* ------------------------ DEPOSIT (FIXED) ------------------------ */

    public ATMDTO deposit(ATMDTO dto) {

        User user = userRepository.findByCardnumber(dto.getAmountCardnumber());
        if (user == null)
            return null;

        int newBalance = user.getRemainingamount() + dto.getAmount();

        user.setRemainingamount(newBalance);
        user.setTotalamount(newBalance);

        userRepository.save(user);

        // Email Template (Deposit)
        String message =
                "Dear Customer,\n\n" +
                        "Thank you for banking with us.\n\n" +
                        "We are pleased to inform you that an amount of ₹" + dto.getAmount() +
                        " has been successfully deposited into your account " + user.getCardnumber() + ".\n\n" +
                        "Your updated balance is ₹" + newBalance + ".\n\n" +
                        "For any queries or support, please contact Customer Care at 1800 1200 1200.\n\n" +
                        "Warm Regards,\n" +
                        "MyBank ATM Services";

        emailService.sendEmail(
                user.getEmail(),
                "Deposit Confirmation",
                message
        );

        return convertToDTO(user);
    }



    /* ------------------------ WITHDRAW (BANK TEMPLATE) ------------------------ */

    public String withdraw(ATMDTO dto) {

        User user = userRepository.findByCardnumber(dto.getAmountCardnumber());
        if (user == null)
            return "Card number not found!";

        if (dto.getAmount() > user.getRemainingamount())
            return "Insufficient Balance!";

        int newBalance = user.getRemainingamount() - dto.getAmount();

        user.setRemainingamount(newBalance);
        user.setTotalamount(newBalance);

        userRepository.save(user);

        // Email Template (Withdraw)
        String message =
                "Dear Customer,\n\n" +
                        "Thank you for banking with us.\n\n" +
                        "This is to notify you that an amount of ₹" + dto.getAmount() +
                        " has been withdrawn from your account " + user.getCardnumber() + ".\n\n" +
                        "Your updated balance is ₹" + newBalance + ".\n\n" +
                        "If this transaction was not made by you, contact Customer Care at 1800 1200 1200 immediately.\n\n" +
                        "Warm Regards,\n" +
                        "MyBank ATM Services";

        emailService.sendEmail(
                user.getEmail(),
                "Withdrawal Alert",
                message
        );

        return "Withdraw Successful! Remaining Balance: ₹" + newBalance;
    }



    /* ------------------------ UPDATE PIN ------------------------ */

    public String updatePin(ATMDTO dto) {

        User user = userRepository.findByCardnumberAndPinnumber(
                dto.getUpdateCardnumber(),
                dto.getOldPin()
        );

        if (user == null)
            return "Invalid old PIN!";

        user.setPinnumber(dto.getNewPin());
        userRepository.save(user);

        emailService.sendEmail(
                user.getEmail(),
                "PIN Updated",
                "Your ATM PIN has been successfully updated."
        );

        return "PIN Updated Successfully!";
    }


    /* ------------------------ DELETE ACCOUNT ------------------------ */

    public boolean deleteUser(String cardnumber) {

        User user = userRepository.findByCardnumber(cardnumber);
        if (user == null)
            return false;

        userRepository.delete(user);

        emailService.sendEmail(
                user.getEmail(),
                "Account Deleted",
                "Your ATM account has been deleted."
        );

        return true;
    }
}
