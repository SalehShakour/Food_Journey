package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.repositories.FoodRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public void addFood(Food food) {
        foodRepository.save(food);
    }

    public Food getFoodById(String id) {
        return foodRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public void deleteFoodById(String id) {
        foodRepository.deleteById(Long.valueOf(id));
    }

    public void updateFoodById(String id, Food updatedFood) {
        Food existingFood = foodRepository.findById(Long.valueOf(id)).orElse(null);
        if (existingFood != null) {
            if (updatedFood.getFoodName() != null) existingFood.setFoodName(updatedFood.getFoodName());
            if (updatedFood.getDescription() != null) existingFood.setDescription(updatedFood.getDescription());
            if (updatedFood.getPrice() != null) existingFood.setPrice(updatedFood.getPrice());
            foodRepository.save(existingFood);
        }
    }
}

