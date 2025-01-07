package com.shoppingApplication.genZ.service;

import com.shoppingApplication.genZ.exception.ApplicationException;
import com.shoppingApplication.genZ.model.Cart;
import com.shoppingApplication.genZ.model.Product;
import com.shoppingApplication.genZ.repositoryTest.CartRepository;
import com.shoppingApplication.genZ.repositoryTest.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public void addToCart(Cart cartDto) {

        Product product = productRepository.findById(cartDto.getProductId())
                .orElseThrow(() -> new ApplicationException("Product not found"));


        if (cartDto.getQuantity() > product.getQuantity()) {
            throw new ApplicationException("Requested quantity exceeds available stock");
        }


        Optional<Cart> existingCartItem = cartRepository.findByUserEmailAndProductId(cartDto.getEmail(), cartDto.getProductId());

        if (existingCartItem.isPresent()) {

            Cart cart = existingCartItem.get();
            int updatedQuantity = cart.getQuantity() + cartDto.getQuantity();
            cart.setQuantity(updatedQuantity);
            cartRepository.updateCart(cart);
        } else {

            Cart cart = new Cart();
            cart.setEmail(cartDto.getEmail());
            cart.setProductId(cartDto.getProductId());
            cart.setQuantity(cartDto.getQuantity());
            cartRepository.addToCart(cart);
        }

    }


    public void addToCart(List<Cart> cartDtos) {
        for (Cart cartDto : cartDtos) {

            Product product = productRepository.findById(cartDto.getProductId())
                    .orElseThrow(() -> new ApplicationException("Product not found"));


            if (cartDto.getQuantity() > product.getQuantity()) {
                throw new ApplicationException("Requested quantity exceeds available stock for product ID: " + cartDto.getProductId());
            }


            Optional<Cart> existingCartItem = cartRepository.findByUserEmailAndProductId(cartDto.getEmail(), cartDto.getProductId());

            if (existingCartItem.isPresent()) {

                Cart existingCart = existingCartItem.get();
                int updatedQuantity = existingCart.getQuantity() + cartDto.getQuantity();
                existingCart.setQuantity(updatedQuantity);
                cartRepository.updateCart(existingCart);
            } else {

                Cart cart = new Cart();
                cart.setEmail(cartDto.getEmail());
                cart.setProductId(cartDto.getProductId());
                cart.setQuantity(cartDto.getQuantity());
                cartRepository.addToCart(cart);
            }
        }
    }





    public List<Cart> viewCart(){
        return cartRepository.viewCart();
    }







}
