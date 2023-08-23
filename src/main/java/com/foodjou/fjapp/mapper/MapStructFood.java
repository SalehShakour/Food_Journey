package com.foodjou.fjapp.mapper;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.dto.FoodDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructFood {
    @Mapping(source = "foodName",target = "foodName")
    Food foodDtoToFood(FoodDTO foodDTO);
    @Mapping(source = "foodName",target = "foodName")
    FoodDTO foodToFoodDTO(Food food);
    @Mapping(source = "foodDTO.foodName",target = "foodName")
    @Mapping(source = "foodDTO.price",target = "price")
    @Mapping(source = "foodDTO.description",target = "description")
    Food updateFoodDtoToFood(FoodDTO foodDTO, Food food);
}
