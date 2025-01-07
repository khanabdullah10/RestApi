package com.mrs.movieReviewSystem.service;

import com.mrs.movieReviewSystem.entity.User;
import com.mrs.movieReviewSystem.exception.UserNotFoundException;
import com.mrs.movieReviewSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;



    public void registerUser(User user) {
        userRepository.save(user);
    }


    public User getUserProfile(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }


    public void updateUserProfile(Long id, User user) {
        User existingUser = getUserProfile(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFullName(user.getFullName());
         userRepository.save(existingUser);
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

