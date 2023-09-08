package com.foodjou.fjapp.mapper.entityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.exception.CustomException;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MapStructRestaurant {
    ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(source = "restaurantName", target = "restaurantName")
    Restaurant restaurantDtoToRestaurant(RestaurantDTO restaurantDTO);
    @Mapping(source = "restaurantName", target = "restaurantName")
    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);

    @Mapping(source = "restaurantDTO.restaurantName", target = "restaurantName")
    @Mapping(source = "restaurantDTO.address", target = "address")
    @Mapping(source = "restaurantDTO.phoneNumber", target = "phoneNumber")
    Restaurant updateRestaurantDtoToRestaurant(RestaurantDTO restaurantDTO, Restaurant restaurant);

    default String updateField(String existingValue, String newValue) {
        return newValue != null ? newValue : existingValue;
    }
    List<RestaurantDTO> restaurantListToRestaurantDtoList(List<Restaurant> restaurants);

}

