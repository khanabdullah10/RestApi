package com.shoppingApplication.genZ.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrder {

    @NotNull
    private String email;
    @NotNull
    private String address;
}
