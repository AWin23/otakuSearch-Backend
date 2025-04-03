package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    private UserService userService;

    // ✅ Register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        // Optional: Add validation
        Users created = userService.createUser(user.getUserName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok(created);
    }

    // ✅ Secure Login using password hashing
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users user) {
        return userService.getUserByEmail(user.getEmail())
                .map(existing -> {
                    // Use bcrypt comparison instead of plain .equals()
                    if (userService.checkPassword(user.getPassword(), existing.getPassword())) {
                        System.out.println("✅ Login successful for: " + user.getEmail());
                        return ResponseEntity.ok("Login success");
                    } else {
                        System.out.println("❌ Invalid password for: " + user.getEmail());
                        return ResponseEntity.status(401).body("Invalid credentials");
                    }
                })
                .orElseGet(() -> {
                    System.out.println("❌ User not found: " + user.getEmail());
                    return ResponseEntity.status(404).body("User not found");
                });
        }

    }
