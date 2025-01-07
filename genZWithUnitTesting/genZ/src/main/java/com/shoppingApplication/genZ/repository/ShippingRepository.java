package com.shoppingApplication.genZ.repository;

import com.shoppingApplication.genZ.model.Shipping;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShippingRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShippingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Shipping shipping) {
        String sql = "INSERT INTO Shipping (order_id, address, shipping_status) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                shipping.getOrderId(),
                shipping.getAddress(),
                shipping.getShippingStatus());
    }


    public Shipping findByOrderId(String orderId) {
        String sql = "SELECT * FROM Shipping WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Shipping.class), orderId);
    }
}

