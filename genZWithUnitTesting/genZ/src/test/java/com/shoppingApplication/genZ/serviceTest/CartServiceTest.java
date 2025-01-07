package com.shoppingApplication.genZ.serviceTest;

import com.shoppingApplication.genZ.exception.ApplicationException;
import com.shoppingApplication.genZ.model.Cart;
import com.shoppingApplication.genZ.model.Product;
import com.shoppingApplication.genZ.repositoryTest.CartRepository;
import com.shoppingApplication.genZ.repositoryTest.ProductRepository;
import com.shoppingApplication.genZ.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private Product mockProduct;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);



        // Setup a mock product with 10 units available in stock
        mockProduct = new Product(1, "Product1", "Description", BigDecimal.valueOf(100.0), 10);
    }

    @Test
    void testAddToCart_NewItem() {
        // Creating a new Cart item
        Cart cartDto = new Cart(1, "testuser@example.com", 1, 2);  // Add 2 units of Product 1

        // Mock the productRepository to return a valid product
        when(productRepository.findById(cartDto.getProductId())).thenReturn(Optional.of(mockProduct));

        // Mock the cartRepository to return an empty cart for the given user and product
        when(cartRepository.findByUserEmailAndProductId(cartDto.getEmail(), cartDto.getProductId()))
                .thenReturn(Optional.empty());

        // Call the service method to add to cart
        cartService.addToCart(cartDto);

        // Capture the cart object passed to cartRepository.addToCart() and verify it
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository, times(1)).addToCart(cartArgumentCaptor.capture());

        Cart savedCart = cartArgumentCaptor.getValue();
        assertNotNull(savedCart);
        assertEquals(cartDto.getEmail(), savedCart.getEmail());
        assertEquals(cartDto.getProductId(), savedCart.getProductId());
        assertEquals(cartDto.getQuantity(), savedCart.getQuantity());
    }






    @Test
    void testAddToCart_ExceedsStock() {
        // Creating a Cart item where the quantity exceeds available stock
        Cart cartDto = new Cart(1, "testuser@example.com", 1, 15);  // Trying to add 15 units of Product 1 (only 10 in stock)

        // Mock the productRepository to return a valid product
        when(productRepository.findById(cartDto.getProductId())).thenReturn(Optional.of(mockProduct));

        // The test should expect an exception when trying to add more than available stock
        ApplicationException exception = assertThrows(ApplicationException.class, () -> cartService.addToCart(cartDto));
        assertEquals("Requested quantity exceeds available stock", exception.getMessage());
    }





    @Test
    void testAddToCart_UpdateExistingItem() {
        // Creating a Cart item that already exists in the cart
        Cart cartDto = new Cart(1, "testuser@example.com", 1, 2);  // Add 2 units of Product 1

        // Mock the productRepository to return a valid product
        when(productRepository.findById(cartDto.getProductId())).thenReturn(Optional.of(mockProduct));

        // Mock cartRepository to return an existing cart item
        Cart existingCart = new Cart(1, "testuser@example.com", 1, 3);  // Existing quantity is 3
        when(cartRepository.findByUserEmailAndProductId(cartDto.getEmail(), cartDto.getProductId()))
                .thenReturn(Optional.of(existingCart));

        // Call the service method to update the existing cart item
        cartService.addToCart(cartDto);

        // Capture the cart object passed to cartRepository.updateCart() and verify it
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository, times(1)).updateCart(cartArgumentCaptor.capture());

        Cart updatedCart = cartArgumentCaptor.getValue();
        assertNotNull(updatedCart);
        assertEquals(cartDto.getEmail(), updatedCart.getEmail());
        assertEquals(cartDto.getProductId(), updatedCart.getProductId());
        assertEquals(5, updatedCart.getQuantity());  // Updated quantity should be 5
    }

    @Test
    void testAddToCart_MultipleItems_ExceedsStock() {
        // Creating multiple Cart items where one exceeds available stock
        Cart cartDto1 = new Cart(1, "testuser@example.com", 1, 15);  // Trying to add 15 units of Product 1 (only 10 in stock)
        Cart cartDto2 = new Cart(2, "testuser@example.com", 2, 8);   // Add 8 units of Product 2

        // Mock the productRepository to return valid products
        when(productRepository.findById(cartDto1.getProductId())).thenReturn(Optional.of(mockProduct));
        when(productRepository.findById(cartDto2.getProductId())).thenReturn(Optional.of(mockProduct));

        // Expecting exception for the first item (exceeds stock)
        ApplicationException exception1 = assertThrows(ApplicationException.class, () -> cartService.addToCart(cartDto1));
        assertEquals("Requested quantity exceeds available stock", exception1.getMessage());

        // The second item should pass, so no exception should be thrown
        cartService.addToCart(cartDto2);

        // Verify that cartRepository.addToCart() was called only once for the second item
        verify(cartRepository, times(1)).addToCart(any(Cart.class));
    }

    @Test
    void testViewCart() {
        // Creating a Cart item
        Cart cart = new Cart(1, "testuser@example.com", 1, 2);  // Mock cart item

        // Mock cartRepository to return a list with one cart item
        when(cartRepository.viewCart()).thenReturn(Arrays.asList(cart));

        // Call the service method to view cart
        var cartItems = cartService.viewCart();

        // Verify the size of the returned list and its content
        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
        assertEquals("testuser@example.com", cartItems.get(0).getEmail());
    }

    @Test
    void testAddToCart_MultipleItems() {
        // Creating multiple Cart items
        Cart cartDto1 = new Cart(1, "testuser@example.com", 1, 3);  // Add 3 units of Product 1
        Cart cartDto2 = new Cart(2, "testuser@example.com", 2, 5);  // Add 5 units of Product 2

        // Mock the productRepository to return valid products
        when(productRepository.findById(cartDto1.getProductId())).thenReturn(Optional.of(mockProduct));
        when(productRepository.findById(cartDto2.getProductId())).thenReturn(Optional.of(mockProduct));

        // Mock cartRepository to return empty cart for each product
        when(cartRepository.findByUserEmailAndProductId(cartDto1.getEmail(), cartDto1.getProductId()))
                .thenReturn(Optional.empty());
        when(cartRepository.findByUserEmailAndProductId(cartDto2.getEmail(), cartDto2.getProductId()))
                .thenReturn(Optional.empty());

        // Call the service method to add multiple items to the cart
        cartService.addToCart(cartDto1);
        cartService.addToCart(cartDto2);

        // Verify that cartRepository.addToCart() was called twice (once for each item)
        verify(cartRepository, times(2)).addToCart(any(Cart.class));  // Expecting 2 invocations
    }
}
