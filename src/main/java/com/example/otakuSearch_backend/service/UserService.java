package com.example.otakuSearch_backend.service;

import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users createUser(String userName, String email, String password) {
        Users user = new Users(userName, email, password);
        return userRepository.save(user);
    }

    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
