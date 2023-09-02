package com.foodjou.fjapp.dto;

import com.foodjou.fjapp.domain.Food;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantResponseDTO {
    private String restaurantName;
    private Long restaurantId;
    private List<Food> foods;
}

