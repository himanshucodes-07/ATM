package com.Atm.Atm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ATMDTO {

    /* ================= CREATE USER ================= */
    private String name;
    private String cardnumber;
    private String pinnumber;
    private Integer totalamount;
    private String email;

    /* ================= LOGIN ================= */
    private String loginCardnumber;
    private String loginPin;

    /* ================= OTP ================= */
    private String emailForOtp;
    private String otp;

    /* ================= DEPOSIT & WITHDRAW ================= */
    private Integer amount;

    /* ================= UPDATE PIN ================= */
    private String updateCardnumber;
    private String oldPin;
    private String newPin;

    /* ================= RESPONSE ================= */
    private Long id;
    private Integer remainingamount;

    /* ================= GETTERS & SETTERS ================= */

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

    public String getEmailForOtp() {
        return emailForOtp;
    }

    public void setEmailForOtp(String emailForOtp) {
        this.emailForOtp = emailForOtp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUpdateCardnumber() {
        return updateCardnumber;
    }

    public void setUpdateCardnumber(String updateCardnumber) {
        this.updateCardnumber = updateCardnumber;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRemainingamount() {
        return remainingamount;
    }

    public void setRemainingamount(Integer remainingamount) {
        this.remainingamount = remainingamount;
    }
}


