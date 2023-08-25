package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.services.FoodService;

import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
@RolesAllowed({"ROLE_ADMIN", "ROLE_RESTAURANT_OWNER", "ROLE_SUPER_ADMIN"})
public class FoodController {

    private final FoodService foodService;
    private final UserRepository userRepository;

    @Autowired
    public FoodController(FoodService foodService, UserRepository userRepository) {
        this.foodService = foodService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> addFood(@Valid @RequestBody Food food) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        Long restaurantId = userRepository.findById(userId).
                orElseThrow(()->new CustomException("User id not found (for get restaurantID)"))
                .getRestaurantId();
        if (restaurantId == null){
            throw new CustomException("You have restaurant owner role, but have not any restaurant :)");
        }

        foodService.addFood(food,restaurantId);
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
    public ResponseEntity<String> updateFoodById(@PathVariable String id, @Valid @RequestBody FoodDTO updatedFoodDTO) {
        foodService.updateFoodById(id, updatedFoodDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
    }
}
