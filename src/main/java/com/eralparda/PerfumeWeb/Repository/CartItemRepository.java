package com.eralparda.PerfumeWeb.Repository;

import com.eralparda.PerfumeWeb.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
