package com.example.otakuSearch_backend.repository;

import com.example.otakuSearch_backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email); // Custom query method to find users by email
}
