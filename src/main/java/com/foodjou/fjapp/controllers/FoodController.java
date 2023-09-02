package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RolesAllowed("ROLE_RESTAURANT_OWNER")
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final UserService userService;


    @PostMapping("/{restaurantId:[0-9]+}")
    public ResponseEntity<String> addFood(@Valid @RequestBody Food food,
                                          @AuthenticationPrincipal User currentUser,
                                          @PathVariable Long restaurantId) {
        Restaurant restaurant = userService.getRestaurantById(currentUser,restaurantId);
        foodService.addFood(food, currentUser, restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food created successfully");
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/{orderId}")
    public ResponseEntity<Food> getFoodById(@PathVariable String orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(foodService.getFoodById(orderId));

    }

    @DeleteMapping("/{restaurantId:[0-9]+}/{orderId}")
    public ResponseEntity<String> deleteFoodById(@PathVariable String orderId,
                                                 @AuthenticationPrincipal User currentUser,
                                                 @PathVariable Long restaurantId) {
        foodService.deleteFood(orderId, currentUser,restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully");
    }

    @PutMapping("/{restaurantId:[0-9]+}/{orderId}")
    public ResponseEntity<String> updateFoodById(@PathVariable String orderId,
                                                 @Valid @RequestBody Food updatedFood,
                                                 @AuthenticationPrincipal User currentUser,
                                                 @PathVariable Long restaurantId) {
        foodService.updateFoodById(orderId, updatedFood, currentUser,restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
    }
}
