package com.shoppingApplication.genZ.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
    private int id;
    @NotNull
    private String  email;
    @NotNull
    private int productId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
