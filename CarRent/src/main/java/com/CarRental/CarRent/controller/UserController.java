package com.CarRental.CarRent.controller;

import java.util.Map;
import java.util.Optional;

import com.CarRental.CarRent.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CarRental.CarRent.Repo.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired(required = true)
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        System.out.println("Login request received:");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            System.out.println("User found in DB:");
            System.out.println("Stored email: " + userOpt.get().getEmail());
            System.out.println("Stored password: " + userOpt.get().getPassword());

            if (userOpt.get().getPassword().equals(password)) {
                return ResponseEntity.ok("Login successful");
            } else {
                System.out.println("❌ Password mismatch");
            }
        } else {
            System.out.println("❌ No user found with this email");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

}
