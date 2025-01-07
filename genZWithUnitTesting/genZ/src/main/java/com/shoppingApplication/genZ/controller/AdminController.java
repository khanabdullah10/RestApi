package com.shoppingApplication.genZ.controller;
import com.shoppingApplication.genZ.model.Product;
import com.shoppingApplication.genZ.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProducts(@RequestBody List<@Valid Product> productDtos) {
        productService.addProducts(productDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Products added successfully");
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @Valid @RequestBody Product productDto) {
        productService.updateProduct(id, productDto);
        return ResponseEntity.ok("Product updated successfully");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/search")
    public List<Product> searchProducts( @RequestParam String keyword){
        return productService.searchProducts(keyword);
    }
}


