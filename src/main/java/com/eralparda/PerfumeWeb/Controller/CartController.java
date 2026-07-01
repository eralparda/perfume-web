package com.eralparda.PerfumeWeb.Controller;

import com.eralparda.PerfumeWeb.DTO.AddToCartRequest;
import com.eralparda.PerfumeWeb.Entity.Cart;
import com.eralparda.PerfumeWeb.Entity.Order;
import com.eralparda.PerfumeWeb.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addItem(@PathVariable Long userId, @RequestBody AddToCartRequest request) {
        return cartService.addItem(userId, request);
    }

    @DeleteMapping("/{userId}/item/{cartItemId}")
    public Cart removeItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        return cartService.removeItem(userId, cartItemId);
    }

    @DeleteMapping("/{userId}/clear")
    public Cart clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }

    @PostMapping("/{userId}/checkout")
    public Order checkout(@PathVariable Long userId) {
        return cartService.checkout(userId);
    }
}
