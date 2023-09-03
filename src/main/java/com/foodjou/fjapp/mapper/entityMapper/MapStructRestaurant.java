package com.foodjou.fjapp.mapper.entityMapper;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MapStructRestaurant {
    @Mapping(source = "restaurantName", target = "restaurantName")
    Restaurant restaurantDtoToRestaurant(RestaurantDTO restaurantDTO);
    @Mapping(source = "restaurantName", target = "restaurantName")
    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);
    @Mapping(source = "restaurantDTO.restaurantName", target = "restaurantName")
    @Mapping(source = "restaurantDTO.address", target = "address")
    @Mapping(source = "restaurantDTO.phoneNumber", target = "phoneNumber")
    Restaurant updateRestaurantDtoToRestaurant(RestaurantDTO restaurantDTO, Restaurant restaurant);
    List<RestaurantDTO> restaurantListToRestaurantDtoList(List<Restaurant> restaurants);

}

