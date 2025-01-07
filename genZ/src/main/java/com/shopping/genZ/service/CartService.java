package com.shopping.genZ.service;

import com.shopping.genZ.dto.CartDto;
import com.shopping.genZ.model.Cart;
import com.shopping.genZ.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void addToCart(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setUserId(cartDto.getUserId());
        cart.setProductId(cartDto.getProductId());
        cart.setQuantity(cartDto.getQuantity());
        cartRepository.addToCart(cart);
    }
}

