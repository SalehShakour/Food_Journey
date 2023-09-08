package com.foodjou.fjapp.dto.entityDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    @NotBlank
    @NotNull
    private String restaurantName;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

}


