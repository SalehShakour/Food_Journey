package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import com.foodjou.fjapp.myEnum.OrderStatus;
import com.foodjou.fjapp.services.FoodOrderService;
import com.foodjou.fjapp.services.OrderService;
import com.foodjou.fjapp.services.RestaurantService;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.services.cache.RestaurantCacheService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantCacheService restaurantCacheService;
    private final OrderService orderService;
    private final FoodOrderService foodOrderService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER')")
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO,
                                                @AuthenticationPrincipal User currentUser) {
        restaurantService.addRestaurant(currentUser, restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
    }

    @PutMapping("/{restaurantId:[0-9]+}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER')")
    public ResponseEntity<String> updateRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO,
                                                   @AuthenticationPrincipal User currentUser,
                                                   @PathVariable Long restaurantId) {
        restaurantService.updateRestaurant(restaurantId,currentUser, restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant updated successfully");
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String restaurantId) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_RESTAURANT_OWNER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<Food>> getRestaurantMenuById(@PathVariable String id) {
        List<Food> menu = restaurantService.getMenu(id);
        return ResponseEntity.status(HttpStatus.OK).body(menu);
    }

    @GetMapping("/{restaurantId:[0-9]+}/orders")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER')")
    public ResponseEntity<List<String>> getAllOrder(@PathVariable Long restaurantId,
                                                    @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(foodOrderService.getFoodOrdersByRestaurantId(currentUser, restaurantId));
    }

    @PutMapping("/orders/{orderId}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER')")
    public ResponseEntity<String> changeOrderStatus(@PathVariable String orderId,
                                                    @AuthenticationPrincipal User currentUser,
                                                    @RequestParam OrderStatus newStatus
    ) {
        restaurantService.findRestaurantsByOwner(currentUser);

        orderService.changeOrderStatus(currentUser, orderId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body("Order status successfully changed to " + newStatus);

    }

    @GetMapping("/menu")
    public ResponseEntity<List<FoodDTO>> getAllRestaurantsWithMenu(@RequestParam(name = "name", required = false) String name,
                                                                   @RequestParam(name = "firstPrice", required = false) String firstPrice,
                                                                   @RequestParam(name = "type", required = false) String type,
                                                                   @RequestParam(name = "secondPrice", required = false) String secondPrice) {
        return ResponseEntity.ok(restaurantService.getAllRestaurantsWithMenu(name,firstPrice,type,secondPrice));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(@RequestParam(name = "name", required = false) String name,
                                                              @RequestParam(name = "address", required = false) String address) {
        return ResponseEntity.ok(restaurantCacheService.getCache(name, address));
    }
}
