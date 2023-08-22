package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    public void addRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }
    public Restaurant getRestaurant(String id){
        return restaurantRepository.findById(Long.valueOf(id)).orElse(null);
    }
    public void deleteRestaurant(String id){
        restaurantRepository.deleteById(Long.valueOf(id));
    }
    public void updateRestaurant(String id, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(Long.valueOf(id)).orElse(null);
        if (existingRestaurant != null) {
            if (updatedRestaurant.getRestaurantName() != null) existingRestaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
            if (updatedRestaurant.getAddress() != null) existingRestaurant.setAddress(updatedRestaurant.getAddress());
            if (updatedRestaurant.getPhoneNumber() != null) existingRestaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
            if (updatedRestaurant.getOwner() != null) existingRestaurant.setOwner(updatedRestaurant.getOwner());
            restaurantRepository.save(existingRestaurant);
        }
    }
    public List<Food> getMenu(String id){
        Restaurant restaurant = restaurantRepository.findById(Long.valueOf(id)).orElse(null);
        if (restaurant != null){
            return restaurant.getFoods();
        }
        return new ArrayList<>();
    }
    public void addFoodToMenu(String id, Food food){
        Restaurant restaurant = restaurantRepository.findById(Long.valueOf(id)).orElse(null);
        if (restaurant != null){
            List<Food> tempFoodList = restaurant.getFoods();
            tempFoodList.add(food);
            restaurant.setFoods(tempFoodList);
            restaurantRepository.save(restaurant);
        }
    }

}
