package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.dto.MenuDTO;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructFood;
import com.foodjou.fjapp.mapper.MapStructMenu;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRestaurant;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MapStructRestaurant mapStructRestaurant;
    private final MapStructFood mapStructFood;
    private final UserRepository userRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MapStructRestaurant mapStructRestaurant, MapStructFood mapStructFood, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mapStructRestaurant = mapStructRestaurant;
        this.mapStructFood = mapStructFood;
        this.userRepository = userRepository;
    }

    public void addRestaurant(String userId, RestaurantDTO restaurantDTO) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomException("User not found"));
        Restaurant restaurant = mapStructRestaurant.restaurantDtoToRestaurant(restaurantDTO);
        restaurant.setOwner(user);
        restaurantRepository.save(restaurant);
    }

    public RestaurantDTO getRestaurant(String id) {
        Restaurant restaurant = restaurantRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("Restaurant not found"));
        return mapStructRestaurant.restaurantToRestaurantDTO(restaurant);
    }

    public void deleteRestaurant(String id) {
        Restaurant restaurant = restaurantRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Restaurant not found"));

        restaurantRepository.delete(restaurant);
    }

    public void updateRestaurant(String id, RestaurantDTO updatedRestaurantDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Restaurant not found"));
        existingRestaurant = mapStructRestaurant.updateRestaurantDtoToRestaurant(updatedRestaurantDTO,existingRestaurant);
        restaurantRepository.save(existingRestaurant);
    }

    public List<FoodDTO> getMenu(String id) {
        Restaurant restaurant = restaurantRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Restaurant not found"));
        List<Food> foodList = restaurant.getFoods();
        List<FoodDTO> foodDTOList = MapStructMenu.INSTANCE.foodsToFoodDTOs(foodList);
        MenuDTO menuDTO = new MenuDTO(foodDTOList);

        return menuDTO.result();
    }

//    public void addFoodToMenu(String id, FoodDTO foodDTO) {
//        Restaurant restaurant = restaurantRepository.findById(Long.valueOf(id))
//                .orElseThrow(() -> new CustomException("Restaurant not found"));
//
//        List<Food> tempFoodList = restaurant.getFoods();
//        tempFoodList.add(mapStructFood.foodDtoToFood(foodDTO));
//        restaurant.setFoods(tempFoodList);
//        restaurantRepository.save(restaurant);
//    }

}
