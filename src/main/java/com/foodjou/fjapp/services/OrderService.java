package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.FoodOrder;
import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public Order orderValidation(String id){
        return orderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Order not found"));
    }
    public Order getOrder(String id){
        return orderValidation(id);
    }


    public Order updateOrder(String id, User user) {
        Order order = orderValidation(id);
        order.setUser(user);

        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        orderRepository.delete(orderValidation(id));
    }
}

