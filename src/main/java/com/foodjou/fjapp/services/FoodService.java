package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructFood;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public Food foodValidation(String id){
        return foodRepository.findById(Long.valueOf(id))
                .orElseThrow(()->new CustomException("food Not found"));
    }

    public void addFood(Food food, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()->new CustomException("Restaurant not found by id"));
        food.setRestaurant(restaurant);
        foodRepository.save(food);
        List<Food> tempList = restaurant.getFoods();
        tempList.add(food);
        restaurant.setFoods(tempList);
        restaurantRepository.save(restaurant);
    }

    public Food getFoodById(String id) {
        return foodValidation(id);
    }

    public void deleteFood(Food food) {
        foodRepository.delete(food);
    }

    public void updateFoodById(String id, Food updatedFood) {
        Food existingFood = foodValidation(id);
        if (updatedFood.getFoodName() != null) existingFood.setFoodName(updatedFood.getFoodName());
        if (updatedFood.getPrice() != null) existingFood.setPrice(updatedFood.getPrice());
        if (updatedFood.getDescription() != null) existingFood.setDescription(updatedFood.getDescription());
        foodRepository.save(existingFood);

    }
}

