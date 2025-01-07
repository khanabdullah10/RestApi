package com.shopping.genZ.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShippingDto {
    @NotNull
    private String orderId;

    @NotNull
    @Size(min = 5, max = 255)
    private String address;
}

