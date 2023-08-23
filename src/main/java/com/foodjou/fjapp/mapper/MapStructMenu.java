package com.foodjou.fjapp.mapper;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.dto.FoodDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapStructMenu {

    MapStructMenu INSTANCE = Mappers.getMapper(MapStructMenu.class);

    @Mapping(target = "foodName", source = "food.foodName")
    @Mapping(target = "price", source = "food.price")
    @Mapping(target = "description", source = "food.description")
    FoodDTO foodToFoodDTO(Food food);

    List<FoodDTO> foodsToFoodDTOs(List<Food> foods);
}
