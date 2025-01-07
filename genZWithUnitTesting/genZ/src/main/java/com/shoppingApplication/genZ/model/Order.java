package com.shoppingApplication.genZ.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @NotNull
    private String id; // UUID for Order ID
    @NotNull
    private String email; // Foreign Key referencing User table
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    @DecimalMin("0.1")
    private BigDecimal totalAmount;
    @NotNull
    @Size(min = 5, max = 20)
    private String status; // e.g., PLACED, SHIPPED, DELIVERED
    private LocalDateTime createdAt;



}
