package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructFood;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRestaurant;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.repositories.specification.FoodSpecification;
import com.foodjou.fjapp.repositories.specification.RestaurantSpecifications;
import com.foodjou.fjapp.services.cache.RestaurantCacheService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MapStructRestaurant mapStructRestaurant;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final MapStructFood mapStructFood;
    private final RestaurantCacheService restaurantCacheService;

    public void addRestaurant(User owner, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = mapStructRestaurant.restaurantDtoToRestaurant(restaurantDTO);
        restaurant.setOwner(owner);
        restaurantRepository.save(restaurant);
        userRepository.save(owner);
    }

    public Restaurant restaurantValidation(String restaurantId) {
        return restaurantRepository.findById(Long.parseLong(restaurantId))
                .orElseThrow(() -> new CustomException("Restaurant not found"));
    }

    public RestaurantDTO getRestaurant(String id) {
        return mapStructRestaurant.restaurantToRestaurantDTO(restaurantValidation(id));
    }

    public void deleteRestaurant(String id) {
        restaurantRepository.delete(restaurantValidation(id));
    }

    public void updateRestaurant(Long restaurantId, User user, RestaurantDTO updatedRestaurantDTO) {
        List<Restaurant> restaurantList = findRestaurantsByOwner(user);
        Restaurant existingRestaurant = restaurantList.stream()
                .filter(restaurant -> restaurant.getId() == restaurantId)
                .findFirst().orElseThrow(() -> new CustomException("List of restaurant is null"));
        existingRestaurant = mapStructRestaurant.updateRestaurantDtoToRestaurant(updatedRestaurantDTO, existingRestaurant);
        restaurantRepository.save(existingRestaurant);
    }

    public List<Food> getMenu(String restaurantId) {
        Restaurant restaurant = restaurantValidation(restaurantId);
        return restaurant.getFoods();
    }

    public List<Restaurant> findRestaurantsByOwner(User currentUser) {
        return restaurantRepository.findByOwner(currentUser).orElseThrow(() -> new CustomException("don't exist this restaurant"));
    }

    public List<RestaurantDTO> getAllRestaurants(String name, String address) {
        Specification<Restaurant> spec = RestaurantSpecifications.searchByFilters(name, address);
        return mapStructRestaurant.restaurantListToRestaurantDtoList(restaurantRepository.findAll(spec));

    }

    public List<FoodDTO> getAllRestaurantsWithMenu(String name, String firstPrice, String type, String secondPrice) {
        Specification<Food> spec = FoodSpecification.searchByFilters(name, firstPrice, type, secondPrice);
        return mapStructFood.toDTOList(foodRepository.findAll(spec));
    }
}
