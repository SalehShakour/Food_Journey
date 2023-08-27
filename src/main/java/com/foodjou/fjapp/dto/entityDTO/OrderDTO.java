package com.foodjou.fjapp.dto.entityDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDTO(@NotBlank List<String> foods) {

}
