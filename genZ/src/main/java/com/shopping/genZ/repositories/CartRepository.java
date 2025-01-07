package com.shopping.genZ.repositories;

import com.shopping.genZ.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addToCart(Cart cart) {
        String sql = "INSERT INTO Cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cart.getUserId(), cart.getProductId(), cart.getQuantity());
    }
}

