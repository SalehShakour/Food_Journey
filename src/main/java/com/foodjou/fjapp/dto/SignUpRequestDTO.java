package com.foodjou.fjapp.dto;

import com.foodjou.fjapp.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequestDTO(@NotNull @NotBlank String username,
                               @NotNull @NotBlank String password,
                               @NotNull @NotBlank String firstname,
                               @NotNull @NotBlank String lastname,
                               @NotNull @NotBlank String phoneNumber,
                               @NotNull @NotBlank String address,
                               Role role) {}
