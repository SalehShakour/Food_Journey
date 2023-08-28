package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.FoodOrder;
import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.OrderDTO;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.FoodOrderRepository;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final FoodOrderRepository foodOrderRepository;


    public Order orderValidation(String id){
        return orderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Order not found"));
    }
    public void createOrder(User currentUser, List<OrderDTO> orderDTOList){
        List<FoodOrder> foodOrderList = new ArrayList<>();
        Order order = Order.builder().user(currentUser).build();

        for (OrderDTO orderDTO: orderDTOList){
            Food food = foodRepository.findById(orderDTO.foodId()).orElseThrow(()->new CustomException("Food not found"));
            FoodOrder foodOrder = FoodOrder.builder().food(food).order(order).quantity(orderDTO.quantity()).build();
            foodOrderRepository.save(foodOrder);
            foodOrderList.add(foodOrder);
        }
        order.setFoodOrders(foodOrderList);
        orderRepository.save(order);

    }
    public Order getOrder(String id){
        return orderValidation(id);
    }


    public Order updateOrder(String id, User user) {
        Order order = orderValidation(id);
        order.setUser(user);

        return orderRepository.save(order);
    }

    public void removeOrder(User currentUser, String id) {
        Order order = orderValidation(id);
        if (Objects.equals(order.getUser().getId(), currentUser.getId())){
            orderRepository.delete(order);
        } else if (currentUser.hasRole("ROLE_ADMIN") || currentUser.hasRole("ROLE_SUPER_ADMIN")) {
            orderRepository.delete(order);
        }else {
            throw new CustomException("you are not allowed to remove an order !");
        }

    }
}

