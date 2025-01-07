package com.shoppingApplication.genZ.service;




import com.shoppingApplication.genZ.model.User;
import com.shoppingApplication.genZ.repositoryTest.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;



    public void registerUser(User user) {
        userRepository.registerUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean loginUser(String username, String password) {
        User user = userRepository.login(username, password);
        return user != null;
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.isEmailRegistered(email);
    }


}

