package com.shopping.genZ.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDto {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}

