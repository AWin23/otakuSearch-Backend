package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
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
    public ResponseEntity<Object> loginUser(@RequestBody Users user) {
        Optional<Users> optionalUser = userService.getUserByEmail(user.getEmail());
    
        return optionalUser
            .<ResponseEntity<Object>>map(existing -> {
                if (userService.checkPassword(user.getPassword(), existing.getPassword())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("userId", existing.getUserId());
                    response.put("email", existing.getEmail());
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
                }
            })
            .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "User not found")));
    }
}
    
