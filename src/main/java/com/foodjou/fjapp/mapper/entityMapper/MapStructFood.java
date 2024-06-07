package com.foodjou.fjapp.mapper.entityMapper;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructFood {
    @Mappings({
            @Mapping(source = "restaurant.id", target = "restaurantId"),
            @Mapping(source = "id", target = "foodId"),
    })
    FoodDTO toDTO(Food food);

    @Mappings({
            @Mapping(source = "restaurantId", target = "restaurant.id"),
            @Mapping(source = "foodId", target = "id"),
    })
    Food toEntity(FoodDTO foodDTO);

    List<FoodDTO> toDTOList(List<Food> foods);

    List<Food> toEntityList(List<FoodDTO> foodDTOs);
}
