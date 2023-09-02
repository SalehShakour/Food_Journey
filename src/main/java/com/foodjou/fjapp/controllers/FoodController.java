package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER')")
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final UserService userService;


    @PostMapping("/{restaurantId:[0-9]+}")
    public ResponseEntity<String> addFood(@Valid @RequestBody Food food,
                                          @AuthenticationPrincipal User currentUser,
                                          @PathVariable Long restaurantId) {
        Restaurant restaurant = userService.getRestaurantById(currentUser,restaurantId);
        foodService.addFood(food, restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food created successfully");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable String foodId) {
        return ResponseEntity.status(HttpStatus.OK).body(foodService.getFoodById(foodId));

    }

    @DeleteMapping("/{restaurantId:[0-9]+}/{foodId:[0-9]+}")
    public ResponseEntity<String> deleteFoodById(@PathVariable String foodId,
                                                 @AuthenticationPrincipal User currentUser,
                                                 @PathVariable Long restaurantId) {
        Restaurant restaurant = userService.getRestaurantById(currentUser, restaurantId);
        foodService.deleteFood(foodId, restaurant);
        return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully");
    }

    @PutMapping("/{restaurantId:[0-9]+}/{foodId}")
    public ResponseEntity<String> updateFoodById(@PathVariable String foodId,
                                                 @Valid @RequestBody Food updatedFood,
                                                 @AuthenticationPrincipal User currentUser,
                                                 @PathVariable Long restaurantId) {
        foodService.updateFoodById(foodId, updatedFood, currentUser,restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
    }
}
