package com.Atm.Atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Atm.Atm.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by card number
    User findByCardnumber(String cardnumber);

    // Login check
    User findByCardnumberAndPinnumber(String cardnumber, String pinnumber);

    // Find user by email
    User findByEmail(String email);

    // Check if cardnumber already exists
    boolean existsByCardnumber(String cardnumber);

    // Check if email exists (Optional but recommended)
    boolean existsByEmail(String email);

    // Delete user directly using card number
    void deleteByCardnumber(String cardnumber);
}
