package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.services.FoodOrderService;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.OrderService;
import com.foodjou.fjapp.services.RestaurantService;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RolesAllowed({"ROLE_ADMIN", "ROLE_RESTAURANT_OWNER", "ROLE_SUPER_ADMIN"})
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final FoodService foodService;
    private final OrderService orderService;
    private final FoodOrderService foodOrderService;

    public boolean validateUserForFoodOrders(String id, User currentUser) {
        return currentUser.hasAnyRoles(new HashSet<>(List.of("ROLE_ADMIN", "ROLE_SUPER_ADMIN"))) ||
                currentUser.getRestaurantId().equals(Long.valueOf(id));
    }


    @PostMapping
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO,
                                                @AuthenticationPrincipal User currentUser) {
        System.out.println(currentUser.getId());
        restaurantService.addRestaurant(currentUser, restaurantDTO);
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
    public ResponseEntity<String> updateRestaurantById(@PathVariable String id,
                                                       @Valid @RequestBody RestaurantDTO updatedRestaurantDTO) {
        restaurantService.updateRestaurant(id, updatedRestaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant updated successfully");
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<Food>> getRestaurantMenuById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getMenu(id));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<String>> getAllOrder(@PathVariable String id,
                                                    @AuthenticationPrincipal User currentUser) {
        if (validateUserForFoodOrders(id, currentUser)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(foodOrderService.getFoodOrdersByRestaurantId(Long.valueOf(id)));
        } else return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @DeleteMapping("{id}/orders/{orderId}")
    public ResponseEntity<String> deleteCompletedOrder(@PathVariable String id,
                                                       @PathVariable String orderId,
                                                       @AuthenticationPrincipal User currentUser) {
        if (validateUserForFoodOrders(id,currentUser)){
            orderService.removeOrder(currentUser,orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
        }
        else return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

}
