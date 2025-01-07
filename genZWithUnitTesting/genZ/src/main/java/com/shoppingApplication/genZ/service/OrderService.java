package com.shoppingApplication.genZ.service;

import com.shoppingApplication.genZ.exception.ApplicationException;
import com.shoppingApplication.genZ.model.*;
import com.shoppingApplication.genZ.repositoryTest.CartRepository;
import com.shoppingApplication.genZ.repositoryTest.OrderRepository;
import com.shoppingApplication.genZ.repositoryTest.ProductRepository;
import com.shoppingApplication.genZ.repositoryTest.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {


@Autowired
CartRepository cartRepository;
@Autowired
ProductRepository productRepository;
@Autowired
OrderRepository orderRepository;
@Autowired
ShippingRepository shippingRepository;

    public String placeOrder(PlaceOrder request) {

        List<Cart> cartItems = cartRepository.findByUserEmail(request.getEmail());
        if (cartItems.isEmpty()) {
            throw new ApplicationException("Cart is empty. Cannot place an order.");
        }


        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ApplicationException("Product not found for ID: " + item.getProductId()));
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }


        Order order = new Order();
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);
        order.setEmail(request.getEmail());
        order.setTotalAmount(totalAmount);
        order.setAddress(request.getAddress());
        order.setStatus("PLACED");
        orderRepository.save(order);


        Shipping shipping = new Shipping();
        shipping.setOrderId(orderId);
        shipping.setAddress(request.getAddress());
        shipping.setShippingStatus("SHIPPED");
        shippingRepository.save(shipping);


        cartRepository.clearCartByEmail(request.getEmail());

        return "Order is placed successfully. Delivery within 5 working days.";
    }

    public Shipping trackShipping(String orderId) {

        return shippingRepository.findByOrderId(orderId);
    }

    public List<Order> getOrderHistory(String email) {
        return orderRepository.getOrderHistory(email);
    }


}
