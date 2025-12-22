package com.Atm.Atm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cardnumber;
    private String pinnumber;
    private int totalamount;
    private int remainingamount;
    private String email;

    // Default constructor
    public User() {}

    // Parameterized constructor (Updated)
    public User(Long id, String name, String cardnumber, String pinnumber,
                int totalamount, int remainingamount, String email) {
        this.id = id;
        this.name = name;
        this.cardnumber = cardnumber;
        this.pinnumber = pinnumber;
        this.totalamount = totalamount;
        this.remainingamount = remainingamount;
        this.email = email;
    }

    //  GETTERS & SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCardnumber() { return cardnumber; }
    public void setCardnumber(String cardnumber) { this.cardnumber = cardnumber; }

    public String getPinnumber() { return pinnumber; }
    public void setPinnumber(String pinnumber) { this.pinnumber = pinnumber; }

    public int getTotalamount() { return totalamount; }
    public void setTotalamount(int totalamount) { this.totalamount = totalamount; }

    public int getRemainingamount() { return remainingamount; }
    public void setRemainingamount(int remainingamount) { this.remainingamount = remainingamount; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
