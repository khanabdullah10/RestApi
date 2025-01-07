package com.shoppingApplication.genZ.repositoryTest;

import com.shoppingApplication.genZ.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderRepositoryTest {

    @InjectMocks
    private OrderRepository orderRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a mock Order object for testing
        order = new Order();
        order.setId("1234");
        order.setEmail("test@example.com");
        order.setTotalAmount(BigDecimal.valueOf(100.50));
        order.setStatus("PLACED");
        order.setAddress("123 Test Street");
    }

    @Test
    void testSaveOrder() {
        // Arrange: We don't need to mock the behavior of JdbcTemplate here as we're just testing if update() is called
        // when the save method is invoked.

        // Act: Call the save method
        orderRepository.save(order);

        // Assert: Verify that jdbcTemplate.update is called once with the correct parameters
        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO Orders (id, email, total_amount, status, address) VALUES (?, ?, ?, ?, ?)"),
                any(Object[].class)); // Verify the update method is called
    }

    @Test
    void testGetOrderHistory() {
        // Arrange: Setup mock behavior for jdbcTemplate.query()
        List<Order> mockOrders = Arrays.asList(order);

        when(jdbcTemplate.query(eq("SELECT * FROM Orders WHERE email = ?"),
                ArgumentMatchers.any(BeanPropertyRowMapper.class), eq("test@example.com"))).thenReturn(mockOrders);

        // Act: Call getOrderHistory method
        List<Order> orders = orderRepository.getOrderHistory("test@example.com");

        // Assert: Verify that jdbcTemplate.query() was called once and return the expected list of orders
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM Orders WHERE email = ?"),
                ArgumentMatchers.any(BeanPropertyRowMapper.class), eq("test@example.com"));

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("test@example.com", orders.get(0).getEmail());
    }
}
