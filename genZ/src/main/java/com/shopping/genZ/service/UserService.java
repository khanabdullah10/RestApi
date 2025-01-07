package com.shopping.genZ.service;

import com.shopping.genZ.dto.LoginDto;
import com.shopping.genZ.dto.UserDto;
import com.shopping.genZ.model.User;
import com.shopping.genZ.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole("USER");
        userRepository.registerUser(user);
    }

    public boolean loginUser(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        return user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
    }
}

