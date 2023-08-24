package com.foodjou.fjapp.dto.entityDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantDTO(@NotBlank @NotNull String restaurantName, @NotBlank String address,
                            @NotBlank String phoneNumber) {
}

