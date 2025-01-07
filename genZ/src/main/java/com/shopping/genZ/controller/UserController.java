    package com.shopping.genZ.controller;

    import com.shopping.genZ.dto.CartDto;
    import com.shopping.genZ.dto.OrderDto;
    import com.shopping.genZ.model.Order;
    import com.shopping.genZ.model.Product;
    import com.shopping.genZ.model.Shipping;
    import com.shopping.genZ.service.CartService;
    import com.shopping.genZ.service.OrderService;
    import com.shopping.genZ.service.ProductService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/user")
    @RequiredArgsConstructor
//    @PreAuthorize("hasRole('USER')")
    public class UserController {

        private final ProductService productService;
        private final CartService cartService;
        private final OrderService orderService;

        @GetMapping("/products")
        public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
            return ResponseEntity.ok(productService.searchProducts(keyword));
        }

       @GetMapping("View")
        public List<Product> getProducts(){
            return productService.getAllProducts();
        }

        @PostMapping("/cart")
        public ResponseEntity<String> addToCart(@RequestBody CartDto cartDto) {
            cartService.addToCart(cartDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart");
        }

        @PostMapping("/order")
        public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto) {
            try {
                orderService.placeOrder(orderDto);
                return ResponseEntity.ok("Order placed successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while placing the order: " + e.getMessage());
            }
        }

        @GetMapping("/orders/{userId}")
        public ResponseEntity<List<Order>> viewOrderHistory(@PathVariable int userId) {
            return ResponseEntity.ok(orderService.getOrderHistory(userId));
        }

        @GetMapping("/shipping/{orderId}")
        public ResponseEntity<Shipping> trackShipping(@PathVariable String orderId) {
            Shipping shippingDetails = orderService.trackShipping(orderId);

            if (shippingDetails != null) {
                return ResponseEntity.ok(shippingDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        }


    }

