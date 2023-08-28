package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.FoodOrder;
import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.FoodOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;

    public FoodOrder foodOrderValidation(String id){
        return foodOrderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("FoodOrder not found"));
    }
    public FoodOrder getFoodOrder(String id){
        return foodOrderValidation(id);
    }


    public FoodOrder createFoodOrder(Food food, Order order) {
        FoodOrder foodOrder = FoodOrder.builder().food(food).order(order).build();
        return foodOrderRepository.save(foodOrder);
    }

    public FoodOrder updateFoodOrder(String id, Food food, Order order) {
        FoodOrder foodOrder = foodOrderValidation(id);
        if (food != null) foodOrder.setFood(food);
        if (order != null) foodOrder.setOrder(order);

        return foodOrderRepository.save(foodOrder);
    }

    public void deleteFoodOrder(String id) {
        foodOrderRepository.delete(foodOrderValidation(id));
    }
}

