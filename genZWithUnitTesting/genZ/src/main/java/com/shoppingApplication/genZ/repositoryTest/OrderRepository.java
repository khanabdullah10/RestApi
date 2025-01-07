package com.shoppingApplication.genZ.repositoryTest;

import com.shoppingApplication.genZ.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Order order) {
        String sql = "INSERT INTO Orders (id, email, total_amount, status, address) " +
                "VALUES (?, ?, ?, ?, ?)";


        String orderId = UUID.randomUUID().toString();


        Object[] params = new Object[] {
                orderId,
                order.getEmail(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getAddress()
        };


        jdbcTemplate.update(sql, params);
    }

    public List<Order> getOrderHistory(String  email) {
        String sql = "SELECT * FROM Orders WHERE email = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class), email);
    }


}
