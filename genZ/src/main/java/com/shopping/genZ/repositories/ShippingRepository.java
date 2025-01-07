package com.shopping.genZ.repositories;

import com.shopping.genZ.model.Shipping;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShippingRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addShippingDetails(Shipping shipping) {
        String sql = "INSERT INTO Shipping (order_id, address, shipping_status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, shipping.getOrderId(), shipping.getAddress(), shipping.getShippingStatus());
    }

    public Shipping getShippingDetailsByOrderId(String orderId) {
        String sql = "SELECT * FROM Shipping WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Shipping.class), orderId);
    }

    public void updateShippingStatus(String orderId, String shippingStatus) {
        String sql = "UPDATE Shipping SET shipping_status = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, shippingStatus, orderId);
    }


    public boolean existsByOrderId(String orderId) {
        String sql = "SELECT COUNT(1) FROM Shipping WHERE order_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, orderId);
        return count != null && count > 0;
    }


    public void update(Shipping shipping) {
        String sql = "UPDATE Shipping SET address = ?, shipping_status = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, shipping.getAddress(), shipping.getShippingStatus(), shipping.getOrderId());
    }



    public void save(Shipping shipping) {

        String sql = "INSERT INTO Shipping (order_id, address, shipping_status) VALUES (?, ?, ?)";


        jdbcTemplate.update(sql, shipping.getOrderId(), shipping.getAddress(), shipping.getShippingStatus());
    }

    public Shipping findByOrderId(String orderId) {
        String sql = "SELECT * FROM Shipping WHERE order_id = ?";
        List<Shipping> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Shipping.class), orderId);


        return results.isEmpty() ? null : results.get(0);
    }



}

