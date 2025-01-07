package com.shoppingApplication.genZ.controller;

import com.shoppingApplication.genZ.exception.ApplicationException;
import com.shoppingApplication.genZ.model.User;
import com.shoppingApplication.genZ.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final UserService userService;
    @PostMapping("register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestHeader String username, @RequestHeader String password) {
        boolean isAuthenticated = userService.loginUser(username, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByUserEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/validate-email")
    public ResponseEntity<String> validateEmail(@RequestHeader("email") String email) {
        try {
            boolean isRegistered = userService.isEmailRegistered(email);
            if (isRegistered) {
                return ResponseEntity.ok("Email is valid as it is registered.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid : Email is not registered.");
            }
        } catch (Exception e) {
            throw new ApplicationException("An error occurred while validating the email: " + e.getMessage());
        }
    }

    @GetMapping("/fetchAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
