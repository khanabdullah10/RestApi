package com.shoppingApplication.genZ.service;

import com.shoppingApplication.genZ.model.Product;
import com.shoppingApplication.genZ.repositoryTest.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

     final ProductRepository productRepository;

     final JdbcTemplate jdbcTemplate;

    public void addProduct(Product productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void updateProduct(int id, Product productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.updateProduct(id, product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public void addProducts(List<Product> productDto) {
        List<Product> products = productDto.stream().map(dto -> {
            Product product = new Product();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity());
            return product;
        }).toList();

        products.forEach(productRepository::addProduct);
    }

    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }
}
