package com.example.service.impl;

import com.example.dto.CartProductDto;
import com.example.dto.UpdateCartProductDto;
import com.example.entity.Cart;
import com.example.entity.CartProduct;
import com.example.entity.SellerProduct;
import com.example.entity.User;
import com.example.repository.CartProductRepository;
import com.example.service.*;
import com.example.util.ApiError;
import com.example.util.CartProductId;
import com.example.util.SellerProductId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductServiceImpl implements CartProductService {
    private final CartProductRepository cartProductRepository;
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final SellerProductService sellerProductService;

    public CartProductServiceImpl(CartProductRepository cartProductRepository, CartService cartService, UserService userService, ProductService productService, SellerProductService sellerProductService) {
        this.cartProductRepository = cartProductRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.sellerProductService = sellerProductService;
    }

    @Override
    public List<CartProduct> getAll() {
        return cartProductRepository.findAll();
    }

    @Override
    public List<CartProduct> getAllByCartId(long cartId) {
        return cartProductRepository.findAllByCartId(cartId);
    }

    @Override
    public List<CartProduct> getAllByCustomer(User authenticatedCustomer) {
        Cart cart = cartService.getByCustomerId(authenticatedCustomer.getId());
        return getAllByCartId(cart.getId());
    }

    @Override
    public CartProduct getById(CartProductId cartProductId) {
        return cartProductRepository.findById(cartProductId).orElseThrow(() -> ApiError.notFound("CartProduct not found with id=" + cartProductId));
    }

    @Override
    public CartProduct getByIdOrNull(CartProductId cartProductId) {
        return cartProductRepository.findById(cartProductId).orElse(null);
    }

    @Override
    public CartProduct save(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProduct save(User authenticatedCustomer, CartProductDto cartProductDto) {
        Cart cart = cartService.getByCustomerId(authenticatedCustomer.getId());
        SellerProduct sellerProduct = sellerProductService.getById(new SellerProductId(cartProductDto.getSellerId(), cartProductDto.getProductId()));
        CartProductId cartProductId = new CartProductId(cart.getId(), sellerProduct.getSeller().getId(), sellerProduct.getProduct().getId());
        CartProduct cartProduct = getByIdOrNull(cartProductId);
        if (cartProduct == null) {
            cartProduct = new CartProduct(cartProductId, cart, sellerProduct.getSeller(), sellerProduct.getProduct(), cartProductDto.getQuantity());
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
        }
        return save(cartProduct);
    }


    @Override
    public CartProduct update(User authenticatedCustomer, long sellerId, long productId, UpdateCartProductDto updateCartProductDto) {
        Cart cart = cartService.getByCustomerId(authenticatedCustomer.getId());
        CartProduct cartProduct = getById(new CartProductId(cart.getId(), sellerId, productId));
        cartService.checkAuthority(authenticatedCustomer, cart);
        cartProduct.setQuantity(updateCartProductDto.getQuantity());
        return save(cartProduct);
    }

    @Override
    public void delete(User authenticatedCustomer, long sellerId, long productId) {
        Cart cart = cartService.getByCustomerId(authenticatedCustomer.getId());
        CartProductId cartProductId = new CartProductId(cart.getId(), sellerId, productId);
        cartService.checkAuthority(authenticatedCustomer, cart);
        cartProductRepository.deleteById(cartProductId);
    }

    @Override
    public void deleteAllByCartId(long cartId) {
        cartProductRepository.deleteAllByCartId(cartId);
    }
}
