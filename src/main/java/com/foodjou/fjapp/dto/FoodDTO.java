package com.foodjou.fjapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record FoodDTO(@NotBlank String foodName,@NotNull Double price,String description) {

}

