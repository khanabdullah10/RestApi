package com.shoppingApplication.genZ.controller;

import com.shoppingApplication.genZ.exception.ApplicationException;
import com.shoppingApplication.genZ.model.*;
import com.shoppingApplication.genZ.service.CartService;
import com.shoppingApplication.genZ.service.OrderService;
import com.shoppingApplication.genZ.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class  UserController {


    private final CartService cartService;

    private final OrderService orderService;

    private final ProductService productService;
    @GetMapping("/ViewProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PostMapping("/cart")
    public ResponseEntity<String> addToCart(@RequestBody @Valid Cart cartDto) {
        cartService.addToCart(cartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to the cart Successfully");
    }
    @PostMapping("/cartList")
    public ResponseEntity<String> addToCart(@RequestBody @Valid List<Cart> cartDto) {
        cartService.addToCart(cartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Items added to the cart Successfully");
    }
    @GetMapping("/viewCart")
    public ResponseEntity<List<Cart>> viewAll() {
        return ResponseEntity.ok(cartService.viewCart());
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid PlaceOrder request) {
        String responseMessage = orderService.placeOrder(request);
        return ResponseEntity.ok(responseMessage);
    }
    @GetMapping("/orders/{email}")
    public ResponseEntity<List<Order>> viewOrderHistory(@PathVariable String email) {
        try {
            return ResponseEntity.ok(orderService.getOrderHistory(email));
        } catch (Exception e) {
            throw new ApplicationException("An error occurred while fetching order history: " + e.getMessage());
        }
    }
    @GetMapping("/shipping/{orderId}")
    public ResponseEntity<Shipping> trackShipping(@PathVariable String orderId) {
        try {
            Shipping shippingDetails = orderService.trackShipping(orderId);
            if (shippingDetails != null) {
                return ResponseEntity.ok(shippingDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            throw new ApplicationException("An error occurred while tracking shipping: " + e.getMessage());
        }
    }

}

