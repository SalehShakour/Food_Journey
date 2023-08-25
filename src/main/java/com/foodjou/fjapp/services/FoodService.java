package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructFood;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final MapStructFood mapStructFood;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository,
                       MapStructFood mapStructFood,
                       RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.mapStructFood = mapStructFood;
        this.restaurantRepository = restaurantRepository;
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

    public FoodDTO getFoodById(String id) {
        Food food = foodRepository.findById(Long.valueOf(id))
                .orElseThrow(()->new CustomException("Not found food"));
        return mapStructFood.foodToFoodDTO(food);
    }

    public void deleteFoodById(String id) {
        Food food = foodRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Restaurant not found"));
        foodRepository.delete(food);
    }

    public void updateFoodById(String id, FoodDTO updatedFoodDTO) {
        Food existingFood = foodRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Food not found"));
        existingFood = mapStructFood.updateFoodDtoToFood(updatedFoodDTO,existingFood);
        foodRepository.save(existingFood);

    }
}

