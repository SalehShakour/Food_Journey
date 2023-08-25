package com.foodjou.fjapp.controllers.entityController;

import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.RestaurantService;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final FoodService foodService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, FoodService foodService) {
        this.restaurantService = restaurantService;
        this.foodService = foodService;
    }

    @PostMapping("/addRestaurant/{userId}")
    public ResponseEntity<String> addRestaurant(@PathVariable String userId, @Valid @RequestBody RestaurantDTO restaurantDTO) {
        restaurantService.addRestaurant(userId, restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String id) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurantByID(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurantById(@PathVariable String id,@Valid @RequestBody RestaurantDTO updatedRestaurantDTO) {
        restaurantService.updateRestaurant(id, updatedRestaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant updated successfully");
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<FoodDTO>> getRestaurantMenuById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getMenu(id));
    }

//    @PutMapping("/addFood")
//    public ResponseEntity<String> addFoodToMenuById(@Valid @RequestBody AddToMenuRequestDTO menuRequestDTO) {
//        FoodDTO foodDTO = foodService.getFoodById(menuRequestDTO.foodId());
//        restaurantService.addFoodToMenu(menuRequestDTO.restaurantId(), foodDTO);
//        return ResponseEntity.status(HttpStatus.OK).body("Food added successfully");
//    }
}
