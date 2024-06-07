package com.foodjou.fjapp.dto.entityDTO;

import com.foodjou.fjapp.myEnum.OrderStatus;
import jakarta.validation.constraints.NotNull;


public record OrderDTO(@NotNull long foodId, @NotNull int quantity, @NotNull OrderStatus orderStatus) {
}
