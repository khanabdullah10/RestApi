package com.shoppingApplication.genZ.controllerTest;

import com.shoppingApplication.genZ.controller.AdminController;
import com.shoppingApplication.genZ.model.Product;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product(1, "Test Product", "Description", BigDecimal.valueOf(10.0), 100);

        // Mocking the service call
        doNothing().when(productService).addProduct(any(Product.class));

        mockMvc.perform(post("/admin/product")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\": \"Test Product\", \"description\": \"Description\", \"price\": 10.0, \"quantity\": 100}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product added successfully"));

        // Verify that service method was called once
        verify(productService, times(1)).addProduct(any(Product.class));
    }

    @Test
    void testAddProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100),
                new Product(2, "Product 2", "Description", BigDecimal.valueOf(15.0), 200)
        );

        // Mocking the service call
        doNothing().when(productService).addProducts(anyList());

        mockMvc.perform(post("/admin/products")
                        .contentType("application/json")
                        .content("[{\"id\": 1, \"name\": \"Product 1\", \"description\": \"Description\", \"price\": 10.0, \"quantity\": 100}, {\"id\": 2, \"name\": \"Product 2\", \"description\": \"Description\", \"price\": 15.0, \"quantity\": 200}]"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Products added successfully"));

        // Verify that service method was called once
        verify(productService, times(1)).addProducts(anyList());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product(1, "Updated Product", "Updated Description", BigDecimal.valueOf(20.0), 50);

        // Mocking the service call
        doNothing().when(productService).updateProduct(anyInt(), any(Product.class));

        mockMvc.perform(put("/admin/product/{id}", 1)
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\": \"Updated Product\", \"description\": \"Updated Description\", \"price\": 20.0, \"quantity\": 50}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product updated successfully"));

        // Verify that service method was called once
        verify(productService, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100),
                new Product(2, "Product 2", "Description", BigDecimal.valueOf(15.0), 200)
        );

        // Mocking the service call
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        // Verify that service method was called once
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Mocking the service call
        doNothing().when(productService).deleteProduct(anyInt());

        mockMvc.perform(delete("/admin/product/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));

        // Verify that service method was called once
        verify(productService, times(1)).deleteProduct(eq(1));
    }

    @Test
    void testSearchProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1, "Product 1", "Description", BigDecimal.valueOf(10.0), 100),
                new Product(2, "Product 2", "Description", BigDecimal.valueOf(15.0), 200)
        );

        // Mocking the service call
        when(productService.searchProducts(anyString())).thenReturn(products);

        mockMvc.perform(get("/admin/search")
                        .param("keyword", "Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        // Verify that service method was called once
        verify(productService, times(1)).searchProducts(anyString());
    }
}