package com.shoppingApplication.genZ.serviceTest;

import com.shoppingApplication.genZ.model.Product;
import com.shoppingApplication.genZ.repositoryTest.ProductRepository;
import com.shoppingApplication.genZ.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setName("Product1");
        product.setDescription("Description1");
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);
    }

    @Test
    void testAddProduct() {
        // Arrange: Mock the addProduct method
        doNothing().when(productRepository).addProduct(any(Product.class));

        // Act: Call the addProduct method
        productService.addProduct(product);

        // Assert: Verify that addProduct was called with the correct argument
        verify(productRepository, times(1)).addProduct(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        // Arrange: Mock the getAllProducts method
        List<Product> products = Arrays.asList(product);
        when(productRepository.getAllProducts()).thenReturn(products);

        // Act: Call the getAllProducts method
        List<Product> result = productService.getAllProducts();

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getName());
    }

    @Test
    void testUpdateProduct() {
        // Arrange: Mock the updateProduct method
        doNothing().when(productRepository).updateProduct(anyInt(), any(Product.class));

        // Act: Call the updateProduct method
        productService.updateProduct(1, product);

        // Assert: Verify that updateProduct was called with the correct arguments
        verify(productRepository, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Arrange: Mock the deleteProduct method
        doNothing().when(productRepository).deleteProduct(anyInt());

        // Act: Call the deleteProduct method
        productService.deleteProduct(1);

        // Assert: Verify that deleteProduct was called with the correct argument
        verify(productRepository, times(1)).deleteProduct(eq(1));
    }

    @Test
    void testSearchProducts() {
        // Arrange: Mock the searchProducts method
        List<Product> products = Arrays.asList(product);
        when(productRepository.searchProducts("Product1")).thenReturn(products);

        // Act: Call the searchProducts method
        List<Product> result = productService.searchProducts("Product1");

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getName());
    }

    @Test
    void testAddProducts() {
        // Arrange: Mock the addProduct method for a list of products
        List<Product> products = Arrays.asList(product);
        doNothing().when(productRepository).addProduct(any(Product.class));

        // Act: Call the addProducts method
        productService.addProducts(products);

        // Assert: Verify that addProduct was called for each product in the list
        verify(productRepository, times(1)).addProduct(any(Product.class));
    }

    @Test
    void testGetProductById() {
        // Arrange: Mock the findById method
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        // Act: Call the getProductById method
        Optional<Product> result = productService.getProductById(1);

        // Assert: Verify the result
        assertTrue(result.isPresent());
        assertEquals("Product1", result.get().getName());
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange: Mock the findById method to return an empty Optional
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act: Call the getProductById method
        Optional<Product> result = productService.getProductById(1);

        // Assert: Verify the result is empty
        assertFalse(result.isPresent());
    }
}
