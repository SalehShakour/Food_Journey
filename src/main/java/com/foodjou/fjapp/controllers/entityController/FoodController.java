package com.foodjou.fjapp.controllers.entityController;

import com.foodjou.fjapp.services.FoodService;

import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("")
    public ResponseEntity<String> addFood(@Valid @RequestBody FoodDTO foodDTO) {
        //Todo get user id and send ot addFood
        foodService.addFood(foodDTO);
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
    public ResponseEntity<String> updateFoodById(@PathVariable String id,@Valid @RequestBody FoodDTO updatedFoodDTO) {
        foodService.updateFoodById(id, updatedFoodDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
    }
}
