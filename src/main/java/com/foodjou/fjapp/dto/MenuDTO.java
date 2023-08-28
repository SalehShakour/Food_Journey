package com.foodjou.fjapp.dto;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;

import java.util.List;

public record MenuDTO(List<Food> foodList) {
    public List<Food> result(){
        return foodList;
    }
}
