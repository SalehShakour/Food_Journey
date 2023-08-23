package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.services.FoodService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("")
    public ResponseEntity<String> addFood(@RequestBody Food food) {
        foodService.addFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable String id) {
        Food food = foodService.getFoodById(id);
        if (food != null) {
            return ResponseEntity.status(HttpStatus.OK).body(food);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodById(@PathVariable String id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFoodById(@PathVariable String id, @RequestBody Food updatedFood) {
        foodService.updateFoodById(id, updatedFood);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");

    }
}
