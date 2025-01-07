package com.shoppingApplication.genZ.repository;

import com.shoppingApplication.genZ.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerUser(User user) {
        String sql = "INSERT INTO User (username, password, email, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isEmailRegistered(String email) {
        String query = "SELECT COUNT(*) FROM User WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, email);
        return count != null && count > 0;
    }


}
