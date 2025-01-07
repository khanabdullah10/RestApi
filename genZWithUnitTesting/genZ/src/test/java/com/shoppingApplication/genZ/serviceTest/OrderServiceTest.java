package com.shoppingApplication.genZ.serviceTest;

import com.shoppingApplication.genZ.exception.ApplicationException;
import com.shoppingApplication.genZ.model.*;
import com.shoppingApplication.genZ.repositoryTest.CartRepository;
import com.shoppingApplication.genZ.repositoryTest.OrderRepository;
import com.shoppingApplication.genZ.repositoryTest.ProductRepository;
import com.shoppingApplication.genZ.repositoryTest.ShippingRepository;
import com.shoppingApplication.genZ.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShippingRepository shippingRepository;

    private PlaceOrder placeOrderRequest;
    private Cart cartItem;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        placeOrderRequest = new PlaceOrder("user@example.com", "123 Street, City");
        cartItem = new Cart(1, "user@example.com", 1, 2);
        product = new Product(1, "Product1","nice", new BigDecimal("10.00"),5);
        order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setEmail("user@example.com");
        order.setTotalAmount(new BigDecimal("20.00"));
        order.setAddress("123 Street, City");
        order.setStatus("PLACED");
    }

    @Test
    void testPlaceOrder_Success() {
        // Arrange: Mock the dependencies
        when(cartRepository.findByUserEmail("user@example.com")).thenReturn(Collections.singletonList(cartItem));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(orderRepository).save(any(Order.class));  // No return value expected
        doNothing().when(shippingRepository).save(any(Shipping.class));  // No return value expected

        // Act: Call the placeOrder method
        String result = orderService.placeOrder(placeOrderRequest);

        // Assert: Check the results
        assertEquals("Order is placed successfully. Delivery within 5 working days.", result);
        verify(cartRepository).clearCartByEmail("user@example.com");
    }

    @Test
    void testPlaceOrder_EmptyCart() {
        // Arrange: Mock an empty cart
        when(cartRepository.findByUserEmail("user@example.com")).thenReturn(Collections.emptyList());

        // Act & Assert: Expect an exception
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            orderService.placeOrder(placeOrderRequest);
        });

        assertEquals("Cart is empty. Cannot place an order.", exception.getMessage());
    }

    @Test
    void testPlaceOrder_ProductNotFound() {
        // Arrange: Mock a cart with an invalid product
        when(cartRepository.findByUserEmail("user@example.com")).thenReturn(Collections.singletonList(cartItem));
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            orderService.placeOrder(placeOrderRequest);
        });

        assertEquals("Product not found for ID: 1", exception.getMessage());
    }

    @Test
    void testTrackShipping_Success() {
        // Arrange: Mock the shipping repository to return shipping details
        Shipping shipping = new Shipping();
        shipping.setOrderId("order-id-123");
        shipping.setShippingStatus("SHIPPED");
        when(shippingRepository.findByOrderId("order-id-123")).thenReturn(shipping);

        // Act: Call trackShipping method
        Shipping result = orderService.trackShipping("order-id-123");

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("SHIPPED", result.getShippingStatus());
    }

    @Test
    void testTrackShipping_OrderNotFound() {
        // Arrange: Mock the shipping repository to return null (order not found)
        when(shippingRepository.findByOrderId("non-existent-id")).thenReturn(null);

        // Act: Call trackShipping method
        Shipping result = orderService.trackShipping("non-existent-id");

        // Assert: Verify that result is null
        assertNull(result);
    }

    @Test
    void testGetOrderHistory_Success() {
        // Arrange: Mock the orderRepository to return a list of orders
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.getOrderHistory("user@example.com")).thenReturn(orders);

        // Act: Call the getOrderHistory method
        List<Order> result = orderService.getOrderHistory("user@example.com");

        // Assert: Verify the order history is fetched
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getId());
    }

    @Test
    void testGetOrderHistory_NoOrders() {
        // Arrange: Mock an empty order history
        when(orderRepository.getOrderHistory("user@example.com")).thenReturn(Collections.emptyList());

        // Act: Call the getOrderHistory method
        List<Order> result = orderService.getOrderHistory("user@example.com");

        // Assert: Verify no orders are returned
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

