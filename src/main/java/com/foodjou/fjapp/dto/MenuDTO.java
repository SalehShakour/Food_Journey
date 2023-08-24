package com.foodjou.fjapp.dto;

import com.foodjou.fjapp.dto.entityDTO.FoodDTO;

import java.util.List;

public record MenuDTO(List<FoodDTO> foodDTOList) {
    public List<FoodDTO> result(){
        return foodDTOList;
    }
}
