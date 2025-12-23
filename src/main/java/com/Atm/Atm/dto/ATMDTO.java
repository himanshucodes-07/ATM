package com.Atm.Atm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * ATMDTO
 * ----------
 * Single DTO used across ATM APIs.
 * Carefully grouped and safe for production use.
 *
 * NOTE:
 * - Used only for request binding
 * - Sensitive fields must NEVER be returned in responses
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ATMDTO {

    /* ================= USER CREATION ================= */
    private String name;
    private String cardnumber;
    private String pinnumber;
    private Integer totalamount;
    private String email;

    /* ================= LOGIN ================= */
    private String loginCardnumber;
    private String loginPin;

    /* ================= OTP ================= */
    private String otp;

    /* ================= TRANSACTIONS ================= */
    private Integer amount;

    /* ================= UPDATE PIN ================= */
    private String oldPin;
    private String newPin;

    /* ================= RESPONSE (READ ONLY) ================= */
    private Long id;
    private Integer balance;

    /* ================= GETTERS & SETTERS ================= */

    // -------- Create User --------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getPinnumber() {
        return pinnumber;
    }

    public void setPinnumber(String pinnumber) {
        this.pinnumber = pinnumber;
    }

    public Integer getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Integer totalamount) {
        this.totalamount = totalamount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // -------- Login --------
    public String getLoginCardnumber() {
        return loginCardnumber;
    }

    public void setLoginCardnumber(String loginCardnumber) {
        this.loginCardnumber = loginCardnumber;
    }

    public String getLoginPin() {
        return loginPin;
    }

    public void setLoginPin(String loginPin) {
        this.loginPin = loginPin;
    }

    // -------- OTP --------
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    // -------- Deposit / Withdraw --------
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    // -------- Update PIN --------
    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    // -------- Response Fields --------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
