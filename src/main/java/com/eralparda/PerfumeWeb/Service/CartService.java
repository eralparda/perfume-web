package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.DTO.AddToCartRequest;
import com.eralparda.PerfumeWeb.DTO.CreateOrderRequest;
import com.eralparda.PerfumeWeb.Entity.Cart;
import com.eralparda.PerfumeWeb.Entity.CartItem;
import com.eralparda.PerfumeWeb.Entity.Order;
import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Entity.User;
import com.eralparda.PerfumeWeb.Exception.BadRequestException;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.CartItemRepository;
import com.eralparda.PerfumeWeb.Repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final PerfumeService perfumeService;
    private final OrderService orderService;

    // Kullanıcının sepetini getir, yoksa yeni oluştur
    public Cart getCart(Long userId) {
        return cartRepository.findByUser_Id(userId)
                .orElseGet(() -> {
                    User user = userService.getUserById(userId);
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    // Sepete ürün ekle (aynı ürün varsa miktarını artır)
    @Transactional
    public Cart addItem(Long userId, AddToCartRequest request) {
        Cart cart = getCart(userId);
        Perfume perfume = perfumeService.getPerfumeById(request.getPerfumeId());

        if (perfume.getStock() < request.getQuantity()) {
            throw new BadRequestException("Yeterli stok yok: " + perfume.getName());
        }

        CartItem existing = cart.getCartItems().stream()
                .filter(item -> item.getPerfume().getId().equals(perfume.getId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setPerfume(perfume);
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(perfume.getPrice());
            cart.getCartItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    // Sepetten tek ürün çıkar
    @Transactional
    public Cart removeItem(Long userId, Long cartItemId) {
        Cart cart = getCart(userId);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("CartItem bulunamadı: " + cartItemId));
        cart.getCartItems().remove(item);
        return cartRepository.save(cart);
    }

    // Sepeti tamamen temizle
    @Transactional
    public Cart clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }

    // Sepeti siparişe çevir ve sepeti temizle
    @Transactional
    public Order checkout(Long userId) {
        Cart cart = getCart(userId);

        if (cart.getCartItems().isEmpty()) {
            throw new BadRequestException("Sepet boş!");
        }

        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setUserId(userId);

        List<Long> perfumeIds = cart.getCartItems().stream()
                .map(item -> item.getPerfume().getId())
                .collect(Collectors.toList());

        List<Integer> quantities = cart.getCartItems().stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.toList());

        orderRequest.setPerfumeIds(perfumeIds);
        orderRequest.setQuantities(quantities);

        Order order = orderService.createOrder(orderRequest);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return order;
    }
}
