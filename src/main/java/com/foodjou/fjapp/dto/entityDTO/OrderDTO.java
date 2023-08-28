package com.foodjou.fjapp.dto.entityDTO;

import jakarta.validation.constraints.NotNull;


public record OrderDTO(@NotNull long foodId,@NotNull int quantity) {
}
