    package com.shopping.genZ.controller;

    import com.shopping.genZ.dto.ProductDto;
    import com.shopping.genZ.model.Product;
    import com.shopping.genZ.service.ProductService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/admin")
    @RequiredArgsConstructor
//    @PreAuthorize("hasRole('ADMIN')")
    public class AdminController {

        private final ProductService productService;

        @PostMapping("/product")
        public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDto) {
            productService.addProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
        }

        @PostMapping("/products")
        public ResponseEntity<String> addProducts(@RequestBody List<@Valid ProductDto> productDtos) {
            productService.addProducts(productDtos);
            return ResponseEntity.status(HttpStatus.CREATED).body("Products added successfully");
        }

        @PutMapping("/product/{id}")
        public ResponseEntity<String> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
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

