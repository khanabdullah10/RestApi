package com.shoppingApplication.genZ.repositoryTest;

import com.shoppingApplication.genZ.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ProductRepositoryTest {

    @InjectMocks
    private ProductRepository productRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a mock Product object for testing
        product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setDescription("Description for Product 1");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setQuantity(10);
    }

    @Test
    void testAddProduct() {
        // Arrange: We don't need to mock jdbcTemplate.update for this test
        // Act: Call the addProduct method
        productRepository.addProduct(product);

        // Assert: Verify that jdbcTemplate.update was called once with the correct parameters
        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO Product (name, description, price, quantity) VALUES (?, ?, ?, ?)"),
                eq(product.getName()), eq(product.getDescription()), eq(product.getPrice()), eq(product.getQuantity()));
    }

    @Test
    void testGetAllProducts() {
        // Arrange: Mock the jdbcTemplate.query() method
        List<Product> mockProducts = Arrays.asList(product);
        when(jdbcTemplate.query(eq("SELECT * FROM Product"), any(BeanPropertyRowMapper.class))).thenReturn(mockProducts);

        // Act: Call the getAllProducts method
        List<Product> products = productRepository.getAllProducts();

        // Assert: Verify the size of the returned list and that it matches the mock data
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product 1", products.get(0).getName());
    }

    @Test
    void testUpdateProduct() {
        // Arrange: We don't need to mock jdbcTemplate.update for this test
        // Act: Call the updateProduct method
        productRepository.updateProduct(1, product);

        // Assert: Verify that jdbcTemplate.update was called with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(eq("UPDATE Product SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?"),
                eq(product.getName()), eq(product.getDescription()), eq(product.getPrice()), eq(product.getQuantity()), eq(1));
    }

    @Test
    void testDeleteProduct() {
        // Arrange: We don't need to mock jdbcTemplate.update for this test
        // Act: Call the deleteProduct method
        productRepository.deleteProduct(1);

        // Assert: Verify that jdbcTemplate.update was called with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM Product WHERE id = ?"), eq(1));
    }

    @Test
    void testSearchProducts() {
        // Arrange: Mock the jdbcTemplate.query() method
        List<Product> mockProducts = Arrays.asList(product);
        when(jdbcTemplate.query(eq("SELECT * FROM Product WHERE name LIKE ?"), any(BeanPropertyRowMapper.class), eq("%Product%"))).thenReturn(mockProducts);

        // Act: Call the searchProducts method
        List<Product> products = productRepository.searchProducts("Product");

        // Assert: Verify that the search returned the correct product
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product 1", products.get(0).getName());
    }

    @Test
    void testFindById() {
        // Arrange: Mock the jdbcTemplate.queryForObject() method
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM Product WHERE id = ?"), any(BeanPropertyRowMapper.class), eq(1))).thenReturn(product);

        // Act: Call the findById method
        Optional<Product> retrievedProduct = productRepository.findById(1);

        // Assert: Verify that the product is found and has the correct ID
        assertTrue(retrievedProduct.isPresent());
        assertEquals(1, retrievedProduct.get().getId());
    }

    @Test
    void testFindById_ProductNotFound() {
        // Arrange: Mock the jdbcTemplate.queryForObject() method to throw an exception
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM Product WHERE id = ?"), any(BeanPropertyRowMapper.class), eq(1)))
                .thenThrow(new RuntimeException("Product not found"));

        // Act: Call the findById method
        Optional<Product> retrievedProduct = productRepository.findById(1);

        // Assert: Verify that the product is not found
        assertFalse(retrievedProduct.isPresent());
    }
}
