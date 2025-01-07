package com.shoppingApplication.genZ.controllerTest;

import com.shoppingApplication.genZ.controller.UserController;
import com.shoppingApplication.genZ.model.*;
import com.shoppingApplication.genZ.service.CartService;
import com.shoppingApplication.genZ.service.OrderService;
import com.shoppingApplication.genZ.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private CartService cartService;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // Test for View Products
    @Test
    void testViewProducts() throws Exception {
        Product product = new Product(1, "Product1", "Description", BigDecimal.valueOf(100.0), 10);
        List<Product> products = Arrays.asList(product);

        // Mocking the service call
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/user/ViewProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[0].price").value(100.0));

        // Verify that the service method was called once
        verify(productService, times(1)).getAllProducts();
    }

    // Test for Add to Cart (Single Item)
    @Test
    void testAddToCart() throws Exception {
        Cart cart = new Cart(1, "testuser@example.com", 1, 2);

        // Mocking the service call
        doNothing().when(cartService).addToCart(any(Cart.class));

        mockMvc.perform(post("/user/cart")
                        .contentType("application/json")
                        .content("{\"email\": \"testuser@example.com\", \"productId\": 1, \"quantity\": 2}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item added to the cart Successfully"));

        // Verify that the service method was called once
        verify(cartService, times(1)).addToCart(any(Cart.class));
    }

    // Test for Add to Cart (Multiple Items)
    @Test
    void testAddToCartMultipleItems() throws Exception {
        Cart cart1 = new Cart(1, "testuser@example.com", 1, 2);
        Cart cart2 = new Cart(2, "testuser@example.com", 2, 1);
        List<Cart> cartList = Arrays.asList(cart1, cart2);

        // Mocking the service call
        doNothing().when(cartService).addToCart(anyList());

        mockMvc.perform(post("/user/cartList")
                        .contentType("application/json")
                        .content("[{\"email\": \"testuser@example.com\", \"productId\": 1, \"quantity\": 2}, {\"email\": \"testuser@example.com\", \"productId\": 2, \"quantity\": 1}]"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Items added to the cart Successfully"));

        // Verify that the service method was called once
        verify(cartService, times(1)).addToCart(anyList());
    }

    // Test for View Cart
    @Test
    void testViewCart() throws Exception {
        Cart cart = new Cart(1, "testuser@example.com", 1, 2);
        List<Cart> cartList = Arrays.asList(cart);

        // Mocking the service call
        when(cartService.viewCart()).thenReturn(cartList);

        mockMvc.perform(get("/user/viewCart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));

        // Verify that the service method was called once
        verify(cartService, times(1)).viewCart();
    }

    // Test for Place Order
    @Test
    void testPlaceOrder() throws Exception {
        PlaceOrder placeOrder = new PlaceOrder("testuser@example.com", "123 Main St");

        // Mocking the service call
        when(orderService.placeOrder(any(PlaceOrder.class))).thenReturn("Order placed successfully");

        mockMvc.perform(post("/user/place")
                        .contentType("application/json")
                        .content("{\"email\": \"testuser@example.com\", \"address\": \"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed successfully"));

        // Verify that the service method was called once
        verify(orderService, times(1)).placeOrder(any(PlaceOrder.class));
    }

    // Test for View Order History
    @Test
    void testViewOrderHistory() throws Exception {
        Order order = new Order("1", "testuser@example.com", "123 Main St", BigDecimal.valueOf(100.0), "PLACED", null);
        List<Order> orders = Arrays.asList(order);

        // Mocking the service call
        when(orderService.getOrderHistory("testuser@example.com")).thenReturn(orders);

        mockMvc.perform(get("/user/orders/{email}", "testuser@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));

        // Verify that the service method was called once
        verify(orderService, times(1)).getOrderHistory("testuser@example.com");
    }

    // Test for Track Shipping
    @Test
    void testTrackShipping() throws Exception {
        Shipping shipping = new Shipping(1, "12345", "123 Main St", "SHIPPED");

        // Mocking the service call
        when(orderService.trackShipping("12345")).thenReturn(shipping);

        mockMvc.perform(get("/user/shipping/{orderId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("12345"))
                .andExpect(jsonPath("$.shippingStatus").value("SHIPPED"));

        // Verify that the service method was called once
        verify(orderService, times(1)).trackShipping("12345");
    }

    // Test for Track Shipping - Not Found
    @Test
    void testTrackShippingNotFound() throws Exception {
        // Mocking the service call for non-existent order
        when(orderService.trackShipping("12345")).thenReturn(null);

        mockMvc.perform(get("/user/shipping/{orderId}", "12345"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        // Verify that the service method was called once
        verify(orderService, times(1)).trackShipping("12345");
    }
}
