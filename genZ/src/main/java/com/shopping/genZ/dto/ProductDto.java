package com.shopping.genZ.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer quantity;
}

