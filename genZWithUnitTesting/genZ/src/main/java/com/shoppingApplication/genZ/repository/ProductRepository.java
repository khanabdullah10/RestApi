package com.shoppingApplication.genZ.repository;

import com.shoppingApplication.genZ.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ProductRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public void addProduct(Product product) {
        String sql = "INSERT INTO Product (name, description, price, quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public void updateProduct(int id, Product product) {
        String sql = "UPDATE Product SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), id);
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Product> searchProducts(String keyword) {
        String sql = "SELECT * FROM Product WHERE name LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), "%" + keyword + "%");
    }

    public Optional<Product> findById(Integer productId) {
        try {
            String query = "SELECT * FROM Product WHERE id = ?";
            Product product = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Product.class), productId);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
