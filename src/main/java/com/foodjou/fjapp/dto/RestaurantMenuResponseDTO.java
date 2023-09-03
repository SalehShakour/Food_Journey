package com.foodjou.fjapp.dto;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantMenuResponseDTO {
    private String restaurantName;
    private Long restaurantId;
    private List<FoodDTO> foods;
}

