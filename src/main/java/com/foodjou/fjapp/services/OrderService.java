package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.FoodOrder;
import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.OrderDTO;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.myEnum.OrderStatus;
import com.foodjou.fjapp.repositories.FoodOrderRepository;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.repositories.OrderRepository;
import jakarta.transaction.Transactional;
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
    private final FoodService foodService;


    public Order orderValidation(String id) {
        return orderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Order not found"));
    }

    @Transactional
    public void createOrder(User currentUser, List<OrderDTO> orderDTOList) {
        List<FoodOrder> foodOrderList = new ArrayList<>();
        Order order = Order.builder().user(currentUser).status(OrderStatus.PENDING).build();
        long firstRestaurantId = foodService.getFoodById(String.valueOf(orderDTOList.get(0).foodId())).getRestaurant().getId();
        for (OrderDTO orderDTO : orderDTOList) {
            Food food = foodRepository.findById(orderDTO.foodId()).orElseThrow(() -> new CustomException("Food not found"));
            if (food.getRestaurant().getId()!=firstRestaurantId){
                throw new CustomException("All foods in one order must belong to one restaurant");
            }
            FoodOrder foodOrder = FoodOrder.builder().food(food).order(order).quantity(orderDTO.quantity()).build();
            foodOrderRepository.save(foodOrder);
            foodOrderList.add(foodOrder);
        }
        order.setFoodOrders(foodOrderList);
        orderRepository.save(order);

    }

    public Order getOrder(String id) {
        return orderValidation(id);
    }


    public Order updateOrder(String id, User user) {
        Order order = orderValidation(id);
        order.setUser(user);

        return orderRepository.save(order);
    }

    public void changeOrderStatus(User currentUser, String orderId, OrderStatus newStatus) {
        Order order = orderValidation(orderId);
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}

