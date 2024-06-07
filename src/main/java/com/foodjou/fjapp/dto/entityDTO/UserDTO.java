package com.foodjou.fjapp.dto.entityDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(@NotNull @NotBlank String email,
                      @NotNull @NotBlank String firstname,
                      @NotNull @NotBlank String lastname,
                      @NotNull @NotBlank String phoneNumber,
                      @NotNull @NotBlank String address) {}
