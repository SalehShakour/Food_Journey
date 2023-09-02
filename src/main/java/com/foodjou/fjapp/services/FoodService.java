package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    public Food foodValidation(String id){
        return foodRepository.findById(Long.valueOf(id))
                .orElseThrow(()->new CustomException("Food Not found"));
    }

    public void addFood(Food food, User currentUser, Restaurant restaurant) {
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

    public void deleteFood(String orderId, User currentUser, Long restaurantId) {
        Restaurant restaurant = userService.getRestaurantById(currentUser, restaurantId);
        Food food = foodValidation(orderId);
        if (food.getRestaurant().getId() != restaurant.getId()){
            throw new CustomException("This food does not belong to your restaurant");
        }
        foodRepository.delete(food);
    }

    public void updateFoodById(String orderId, Food updatedFood ,User currentUser, Long RestaurantId) {
        Food existingFood = foodValidation(orderId);
        Restaurant restaurant = userService.getRestaurantById(currentUser,RestaurantId);
        if (existingFood.getRestaurant().getId() != restaurant.getId()){
            throw new CustomException("This food does not belong to your restaurant");
        }
        if (updatedFood.getFoodName() != null) existingFood.setFoodName(updatedFood.getFoodName());
        if (updatedFood.getPrice() != null) existingFood.setPrice(updatedFood.getPrice());
        if (updatedFood.getDescription() != null) existingFood.setDescription(updatedFood.getDescription());
        foodRepository.save(existingFood);
    }
}

