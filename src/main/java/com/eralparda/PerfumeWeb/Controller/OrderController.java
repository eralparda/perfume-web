package com.eralparda.PerfumeWeb.Controller;

import com.eralparda.PerfumeWeb.DTO.CreateOrderRequest;
import com.eralparda.PerfumeWeb.Entity.Order;
import com.eralparda.PerfumeWeb.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request){
        return orderService.createOrder(request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId){
        return orderService.getOrdersByUser(userId);
    }

}
