package com.foodjou.fjapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddToMenuRequestDTO(
     @NotNull @NotBlank String restaurantId,
     @NotNull @NotBlank String foodId

){}

