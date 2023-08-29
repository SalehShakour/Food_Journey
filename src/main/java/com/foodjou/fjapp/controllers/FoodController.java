package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.services.FoodService;

import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@RolesAllowed({"ROLE_ADMIN", "ROLE_RESTAURANT_OWNER", "ROLE_SUPER_ADMIN"})
@AllArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<String> addFood(@Valid @RequestBody Food food,
                                          @AuthenticationPrincipal User currentUser) {
        Long restaurantId = currentUser.getRestaurantId();
        if (restaurantId == null) {
            throw new CustomException("You have restaurant owner role, but have not any restaurant :)");
        }

        foodService.addFood(food, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable String id) {
        FoodDTO foodDTO = foodService.getFoodById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foodDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodById(@PathVariable String id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFoodById(@PathVariable String id,
                                                 @Valid @RequestBody FoodDTO updatedFoodDTO) {
        foodService.updateFoodById(id, updatedFoodDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
    }
}
