package com.shoppingApplication.genZ.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {

    @NotNull
    private Integer id;
    @NotNull
    private String orderId;
    @NotBlank
    private String address;
    private String shippingStatus;
}
