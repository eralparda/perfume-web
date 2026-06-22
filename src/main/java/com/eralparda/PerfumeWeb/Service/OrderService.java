package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.DTO.CreateOrderRequest;
import com.eralparda.PerfumeWeb.Entity.Order;
import com.eralparda.PerfumeWeb.Entity.OrderItem;
import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Entity.User;
import com.eralparda.PerfumeWeb.Exception.BadRequestException;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PerfumeService perfumeService;
    private final UserService userService;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Order not found with id! " + id));
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request){
        User user = userService.getUserById(request.getUserId());

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice=0;

        for(int i=0; i<request.getPerfumeIds().size(); i++){

            //PERFUME GETİR
            Perfume perfume = perfumeService.getPerfumeById(request.getPerfumeIds().get(i));
            int qty = request.getQuantities().get(i);

            //STOK KONTROL
            if(perfume.getStock()<qty){
                throw new BadRequestException("Not enough stock for: " + perfume.getName());
            }

            //STOK DÜŞ
            perfumeService.decreaseStock(perfume.getId(),qty);

            //ORDERITEM OLUŞTUR
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPerfume(perfume);
            item.setQuantity(qty);
            item.setPrice(perfume.getPrice());

            //LİSTEYE EKLE
            orderItems.add(item);

            //TOPLAM FİYAT HESAPLA
            totalPrice += perfume.getPrice() * qty;
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUser(Long userId){
        return orderRepository.findByUser_Id(userId);
    }
}
