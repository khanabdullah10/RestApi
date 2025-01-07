package com.shoppingApplication.genZ.repository;

import com.shoppingApplication.genZ.model.Cart;
import com.shoppingApplication.genZ.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addToCart(Cart cart) {
        String sql = "INSERT INTO Cart (email, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cart.getEmail(), cart.getProductId(), cart.getQuantity());
    }

    public Optional<Cart> findByUserEmailAndProductId(String email, Integer productId) {
        String query = "SELECT * FROM cart WHERE email = ? AND product_id = ?";

        List<Cart> carts = jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Cart.class), email, productId);


        return carts.isEmpty() ? Optional.empty() : Optional.of(carts.get(0));
    }


    public void updateCart(Cart cart) {
        String sql = "UPDATE cart SET quantity = ? WHERE email = ? AND product_id = ?";
        jdbcTemplate.update(sql, cart.getQuantity(), cart.getEmail(), cart.getProductId());
    }


    public List<Cart> viewCart(){
        String sql = "select * from Cart";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cart.class));
    }

    public List<Cart> findByUserEmail(String email) {
        String query = "SELECT * FROM cart WHERE email = ? ";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Cart.class), email);
    }

    public void clearCartByEmail(String email) {
        String sql = "DELETE FROM Cart WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }


}
