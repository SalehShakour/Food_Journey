package com.foodjou.fjapp.dto;


import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

public record RestaurantDTO(@NotBlank @NotNull String restaurantName, @NotBlank String address,
                            @NotBlank String phoneNumber,
                            @NotBlank String ownerName) {
}

