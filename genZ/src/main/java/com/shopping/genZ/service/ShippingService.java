package com.shopping.genZ.service;

import com.shopping.genZ.dto.ShippingDto;
import com.shopping.genZ.model.Shipping;
import com.shopping.genZ.repositories.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final JdbcTemplate jdbcTemplate;

    private final ShippingRepository shippingRepository;

    public void addShippingDetails(String orderId, String address) {

        System.out.println("Adding shipping details: Order ID = " + orderId + ", Address = " + address);


        Shipping shipping = new Shipping(orderId, address, "PENDING"); // Default status is PENDING


        System.out.println("Saving shipping details: " + shipping);


        shippingRepository.save(shipping);
    }

    public void save(Shipping shipping) {




        String sql = "INSERT INTO Shipping (order_id, address, shipping_status) VALUES (?, ?, ?)";


        jdbcTemplate.update(sql, shipping.getOrderId(), shipping.getAddress(), shipping.getShippingStatus());
    }






}

