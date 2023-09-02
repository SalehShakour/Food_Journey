package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.services.FoodService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@RolesAllowed("ROLE_RESTAURANT_OWNER")
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<String> addFood(@Valid @RequestBody Food food,
                                          @AuthenticationPrincipal User currentUser) {
        foodService.addFood(food, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food created successfully");
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(foodService.getFoodById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodById(@PathVariable String id,
                                                 @AuthenticationPrincipal User currentUser) {
        foodService.deleteFood(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFoodById(@PathVariable String id,
                                                 @Valid @RequestBody Food updatedFood,
                                                 @AuthenticationPrincipal User currentUser) {
        foodService.updateFoodById(id, updatedFood, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
    }
}
