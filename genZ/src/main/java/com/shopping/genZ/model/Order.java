package com.shopping.genZ.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NotNull
    private String id;

    @NotNull
    private int userId;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal totalAmount;

    @NotNull
    @Size(min = 5, max = 20)
    private String status;
}

