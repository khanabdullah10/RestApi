package com.shopping.genZ.repositories;

import com.shopping.genZ.model.Order;
import com.shopping.genZ.model.Shipping;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public void placeOrder(Order order) {
        String sql = "INSERT INTO Orders (id, user_id, total_amount, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getId(), order.getUserId(), order.getTotalAmount(), order.getStatus());
    }

    public List<Order> getOrderHistory(int userId) {
        String sql = "SELECT * FROM Orders WHERE user_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class), userId);
    }

    public Shipping trackShipping(String orderId) {

        String sql = "SELECT * FROM Shipping WHERE order_id = ?";


        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Shipping.class), orderId);
    }

}

