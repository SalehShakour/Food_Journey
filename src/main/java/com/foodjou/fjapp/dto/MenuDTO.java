package com.foodjou.fjapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MenuDTO(List<FoodDTO> foodDTOList) {
    public List<FoodDTO> result(){
        return foodDTOList;
    }
}
