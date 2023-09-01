package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.dto.MenuDTO;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.MapStructMenu;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRestaurant;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MapStructRestaurant mapStructRestaurant;
    private final UserRepository userRepository;

    public void addRestaurant(User owner, RestaurantDTO restaurantDTO) {
        try {
            Restaurant restaurant = getRestaurantOwner(owner);
        }catch (CustomException exception){
            Restaurant restaurant = mapStructRestaurant.restaurantDtoToRestaurant(restaurantDTO);
            restaurant.setOwner(owner);
            restaurantRepository.save(restaurant);
            userRepository.save(owner);
        }

    }
    public Restaurant restaurantValidation(String id){
        return restaurantRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("Restaurant not found"));
    }

    public RestaurantDTO getRestaurant(String id) {
        return mapStructRestaurant.restaurantToRestaurantDTO(restaurantValidation(id));
    }

    public void deleteRestaurant(String id) {
        restaurantRepository.delete(restaurantValidation(id));
    }

    public void updateRestaurant(String id, RestaurantDTO updatedRestaurantDTO) {
        Restaurant existingRestaurant = restaurantValidation(id);
        existingRestaurant = mapStructRestaurant.updateRestaurantDtoToRestaurant(updatedRestaurantDTO,existingRestaurant);
        restaurantRepository.save(existingRestaurant);
    }

    public List<Food> getMenu(String id) {
        Restaurant restaurant = restaurantValidation(id);
        List<Food> foodList = restaurant.getFoods();
        MenuDTO menuDTO = new MenuDTO(foodList);

        return menuDTO.result();
    }
    public Restaurant getRestaurantOwner(User currentUser){
        return restaurantRepository.findByOwner(currentUser).orElseThrow(()-> new CustomException("don't exist restaurant with this owner"));
    }

}
