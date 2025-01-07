package com.shopping.genZ.dto;

import com.shopping.genZ.model.Shipping;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    @NotNull
    private int userId;
    private List<CartItemDto> cartItems;
    private String address;
    private BigDecimal totalAmount;

}


