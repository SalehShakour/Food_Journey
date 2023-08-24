package com.foodjou.fjapp.dto.entityDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RoleDTO(@NotBlank @NotNull String roleName,String description) {
}

