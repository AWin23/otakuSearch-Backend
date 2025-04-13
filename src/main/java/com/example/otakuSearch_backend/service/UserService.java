package com.example.otakuSearch_backend.service;

import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Users createUser(String userName, String email, String password) {
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(password);
        Users user = new Users(userName, email, hashedPassword);
        Users savedUser = userRepository.save(user);

        // ✅ Log success
        System.out.println("✅ User created: " + savedUser.getEmail() + " (ID: " + savedUser.getUserId() + ")");
        return userRepository.save(user);
    }

    // logs user's email for debuggin
    public Optional<Users> getUserByEmail(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);

        // 👀 Log email lookup
        if (userOpt.isPresent()) {
            System.out.println("🔍 Found user with email: " + email);
        } else {
            System.out.println("❌ No user found with email: " + email);
        }

        return userOpt;
    }
    // Optionally add this method if you want to check passwords during login
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        boolean match = passwordEncoder.matches(rawPassword, hashedPassword);

        // 🧠 Log password match results
        if (match) {
            System.out.println("✅ Password match successful.");
        } else {
            System.out.println("❌ Password mismatch.");
        }

        return match;
    }

    // fetches the user in the database by ID
    public Users getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("❌ User not found with ID: " + userId));
    }
    
}
