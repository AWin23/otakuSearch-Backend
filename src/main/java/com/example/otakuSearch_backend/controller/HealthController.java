package com.example.otakuSearch_backend.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
