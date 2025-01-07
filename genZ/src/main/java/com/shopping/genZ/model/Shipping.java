package com.shopping.genZ.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {
    private int id;

    @NotNull
    private String orderId;

    @NotNull
    private String address;

    @NotNull
    private String shippingStatus;


    public Shipping(String orderId, String address, String shippingStatus) {
        this.orderId = orderId;
        this.address = address;
        this.shippingStatus = shippingStatus;
    }


}

