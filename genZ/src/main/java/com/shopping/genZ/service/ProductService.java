package com.shopping.genZ.service;

import com.shopping.genZ.dto.ProductDto;
import com.shopping.genZ.model.Product;
import com.shopping.genZ.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;
    public void addProduct(ProductDto productDto) {
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

    public void updateProduct(int id, ProductDto productDto) {
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

    public void addProducts(List<ProductDto> productDtos) {
        List<Product> products = productDtos.stream().map(dto -> {
            Product product = new Product();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setQuantity(dto.getQuantity());
            return product;
        }).toList();

        products.forEach(productRepository::addProduct);
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM Product WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productId);

        }
    }


