package com.shoppingApplication.genZ.repositoryTest;

import com.shoppingApplication.genZ.model.Shipping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ShippingRepositoryTest {

    @InjectMocks
    private ShippingRepository shippingRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private Shipping shipping;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a mock Shipping object for testing
        shipping = new Shipping();
        shipping.setId(1);
        shipping.setOrderId("12345");
        shipping.setAddress("123 Street, City, Country");
        shipping.setShippingStatus("SHIPPED");
    }

    @Test
    void testSave() {
        // Arrange: We don't need to mock jdbcTemplate.update for this test
        // Act: Call the save method
        shippingRepository.save(shipping);

        // Assert: Verify that jdbcTemplate.update was called with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO Shipping (order_id, address, shipping_status) VALUES (?, ?, ?)"),
                eq(shipping.getOrderId()), eq(shipping.getAddress()), eq(shipping.getShippingStatus()));
    }

    @Test
    void testFindByOrderId() {
        // Arrange: Mock the jdbcTemplate.queryForObject() method to return the mock shipping record
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM Shipping WHERE order_id = ?"), any(BeanPropertyRowMapper.class), eq("12345")))
                .thenReturn(shipping);

        // Act: Call the findByOrderId method
        Shipping retrievedShipping = shippingRepository.findByOrderId("12345");

        // Assert: Verify that the retrieved shipping matches the mock shipping data
        assertNotNull(retrievedShipping);
        assertEquals("12345", retrievedShipping.getOrderId());
        assertEquals("123 Street, City, Country", retrievedShipping.getAddress());
        assertEquals("SHIPPED", retrievedShipping.getShippingStatus());
    }

    @Test
    void testFindByOrderId_NotFound() {
        // Arrange: Mock jdbcTemplate.queryForObject to throw EmptyResultDataAccessException (simulate no result)
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM Shipping WHERE order_id = ?"), any(BeanPropertyRowMapper.class), eq("12345")))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert: Expect a RuntimeException when no result is found
        assertThrows(RuntimeException.class, () -> {
            shippingRepository.findByOrderId("12345");
        });
    }

    @Test
    void testFindByOrderId_Found() {
        // Arrange: Mock jdbcTemplate.queryForObject to return a mock Shipping object
        Shipping mockShipping = new Shipping();
        mockShipping.setOrderId("12345");
        mockShipping.setAddress("123 Main St");
        mockShipping.setShippingStatus("SHIPPED");
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM Shipping WHERE order_id = ?"), any(BeanPropertyRowMapper.class), eq("12345")))
                .thenReturn(mockShipping);

        // Act: Call findByOrderId with an existing orderId
        Shipping result = shippingRepository.findByOrderId("12345");

        // Assert: Verify that the shipping info is returned correctly
        assertNotNull(result);
        assertEquals("12345", result.getOrderId());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("SHIPPED", result.getShippingStatus());
    }


}
