package com.foodjou.fjapp.dto.entityDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record FoodDTO(@NotNull Long restaurantId,
                      @NotNull Long foodId,
                      @NotBlank String foodName,
                      @NotNull Double price,
                      String description) {

}

