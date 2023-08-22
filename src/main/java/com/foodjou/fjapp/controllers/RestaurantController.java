package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }

    private FoodService foodService;
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @PostMapping("")
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant){
        try {
            restaurantService.addRestaurant(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant data is not valid");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id){
        try {
            Restaurant restaurant = restaurantService.getRestaurant(id);
            if (restaurant != null) {
                return ResponseEntity.status(HttpStatus.OK).body(restaurant);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurantByID(@PathVariable String id){
        try {
            Restaurant restaurant = restaurantService.getRestaurant(id);
            if (restaurant != null) {
                restaurantService.deleteRestaurant(id);
                return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurantById(@PathVariable String id, @RequestBody Restaurant updatedRestaurant) {
        try {
            restaurantService.updateRestaurant(id, updatedRestaurant);
            return ResponseEntity.status(HttpStatus.OK).body("Restaurant updated successfully");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<Food>> getRestaurantMenuById(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getMenu(id));
        }catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/{id}/addFood")
    public ResponseEntity<String> addFoodToMenuById(@PathVariable String id,@RequestBody Food food){
        try {
            foodService.addFood(food);
            restaurantService.addFoodToMenu(id,food);
            return ResponseEntity.status(HttpStatus.OK).body("Food added successfully");
        }catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
