package com.shopping.genZ.service;

import com.shopping.genZ.dto.CartItemDto;
import com.shopping.genZ.dto.OrderDto;
import com.shopping.genZ.model.Order;
import com.shopping.genZ.model.Product;
import com.shopping.genZ.model.Shipping;
import com.shopping.genZ.repositories.OrderRepository;
import com.shopping.genZ.repositories.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShippingRepository shippingRepository;
    private final ShippingService shippingService;
    private final ProductService productService;

    public void placeOrder(OrderDto orderDto) {

        for (CartItemDto cartItem : orderDto.getCartItems()) {
            Product product = productService.getProductById(cartItem.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Product not found for ID: " + cartItem.getProductId());
            }
        }


        BigDecimal totalAmount = orderDto.getCartItems().stream()
                .map(cartItem -> {
                    Product product = productService.getProductById(cartItem.getProductId());
                    return product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(orderDto.getUserId());
        order.setTotalAmount(totalAmount);
        order.setStatus("PLACED");


        orderRepository.placeOrder(order);


        boolean shippingExists = shippingRepository.existsByOrderId(order.getId());

        Shipping shipping;
        if (!shippingExists) {

            shipping = new Shipping();
            shipping.setOrderId(order.getId());
            shipping.setAddress(orderDto.getAddress());
            shipping.setShippingStatus("PENDING");


            shippingRepository.save(shipping);
        } else {

            shipping = shippingRepository.findByOrderId(order.getId());
            if (shipping != null) {

                shipping.setAddress(orderDto.getAddress());
                shipping.setShippingStatus("PENDING");


                shippingRepository.update(shipping);
            } else {

                throw new IllegalStateException("Shipping record found in DB, but not fetched correctly.");
            }
        }


        shippingService.addShippingDetails(order.getId().toString(), orderDto.getAddress());
    }


    public List<Order> getOrderHistory(int userId) {
        return orderRepository.getOrderHistory(userId);
    }

    public Shipping trackShipping(String orderId) {
        return shippingRepository.findByOrderId(orderId);
    }





}

