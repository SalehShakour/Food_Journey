package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructFood;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final MapStructFood mapStructFood;

    @Autowired
    public FoodService(FoodRepository foodRepository, MapStructFood mapStructFood) {
        this.foodRepository = foodRepository;
        this.mapStructFood = mapStructFood;
    }

    public void addFood(FoodDTO foodDTO) {
        //Todo update restaurant menu
        foodRepository.save(mapStructFood.foodDtoToFood(foodDTO));
    }

    public FoodDTO getFoodById(String id) {
        Food food = foodRepository.findById(Long.valueOf(id)).orElseThrow(()->new CustomException("Not found food"));
        return mapStructFood.foodToFoodDTO(food);
    }

    public void deleteFoodById(String id) {
        Food food = foodRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("Restaurant not found"));
        foodRepository.delete(food);
    }

    public void updateFoodById(String id, FoodDTO updatedFoodDTO) {
        Food existingFood = foodRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("Food not found"));
        existingFood = mapStructFood.updateFoodDtoToFood(updatedFoodDTO,existingFood);
        foodRepository.save(existingFood);

    }
}

