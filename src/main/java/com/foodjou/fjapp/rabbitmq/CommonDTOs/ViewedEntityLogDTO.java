package com.foodjou.fjapp.rabbitmq.CommonDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public record ViewedEntityLogDTO(@NotNull Long entityId,
                                 @NotBlank String requester,
                                 @NotNull LocalDateTime time) {
}