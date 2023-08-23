package com.foodjou.fjapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RoleDTO(@NotBlank @NotNull String roleName,String description) {
}

