package com.shoppingApplication.genZ.repositoryTest;

import com.shoppingApplication.genZ.model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CartRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CartRepository cartRepository;

    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart(1, "test@example.com", 1001, 3);
    }

    @Test
    public void testAddToCart() {
        // Use when() for mock that returns void methods
        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        cartRepository.addToCart(cart);

        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO Cart (email, product_id, quantity) VALUES (?, ?, ?)"),
                eq(cart.getEmail()), eq(cart.getProductId()), eq(cart.getQuantity()));
    }

    @Test
    public void testFindByUserEmailAndProductId_Exist() {
        // Mock jdbcTemplate.query to return the cart in the list
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any(), any()))
                .thenReturn(List.of(cart));

        Optional<Cart> result = cartRepository.findByUserEmailAndProductId(cart.getEmail(), cart.getProductId());

        // Verify that the result is present
        assertTrue(result.isPresent());
        assertEquals(cart.getEmail(), result.get().getEmail());
        assertEquals(cart.getProductId(), result.get().getProductId());
    }

    @Test
    public void testFindByUserEmailAndProductId_NotExist() {
        // Mock jdbcTemplate.query to return an empty list (no cart found)
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any(), any()))
                .thenReturn(List.of());

        Optional<Cart> result = cartRepository.findByUserEmailAndProductId(cart.getEmail(), cart.getProductId());

        // Verify that the result is empty
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateCart() {
        // Use when() for mock that returns void methods
        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);

        cartRepository.updateCart(cart);

        // Verify the update method was called correctly
        verify(jdbcTemplate, times(1)).update(eq("UPDATE cart SET quantity = ? WHERE email = ? AND product_id = ?"),
                eq(cart.getQuantity()), eq(cart.getEmail()), eq(cart.getProductId()));
    }

    @Test
    public void testViewCart() {
        // Mock query to return a list with one cart
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class)))
                .thenReturn(List.of(cart));

        List<Cart> result = cartRepository.viewCart();

        // Verify that the list contains one cart
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cart.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void testFindByUserEmail() {
        // Mock query to return a list with one cart
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any()))
                .thenReturn(List.of(cart));

        List<Cart> result = cartRepository.findByUserEmail(cart.getEmail());

        // Verify that the list contains one cart
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cart.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void testClearCartByEmail() {
        // Use when() for mock that returns void methods
        when(jdbcTemplate.update(anyString(), Optional.ofNullable(any()))).thenReturn(1);

        cartRepository.clearCartByEmail(cart.getEmail());

        // Verify that the update method was called with the correct parameters
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM Cart WHERE email = ?"), eq(cart.getEmail()));
    }
}